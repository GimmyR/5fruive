const addToCart = async (product) => {
	const inputAddToCart = document.getElementById("input-add-to-cart-" + product.id);
	
	const data = new FormData();
	data.append("productId", product.id);
	data.append("amount", inputAddToCart.value);
	
	const response = await fetch("/api/cart/add", { 
		method: "POST",
		body: data
	});
	
	if(!response.ok)
		console.log(response.status);
	
	const res = await response.json();
	displayCartCounter(res.data);
};

const displayCartCounter = (size) => {
	const cartCounter = document.getElementById("cart-counter");
	if(size > 0) {
		cartCounter.innerText = size;
		cartCounter.style.display = "block";
	} else cartCounter.style.display = "none";
};

const getCartSize = async () => {
	const response = await fetch("/api/cart-size");
	const res = await response.json();
	displayCartCounter(res.data);
};

const removeProduct = async (product) => {
	console.log("Remove this : " + product.id);
};

const resetCart = () => {
	document.location.replace("/cart/reset");
};

getCartSize();