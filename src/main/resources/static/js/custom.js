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
	const response = await fetch("/api/cart/remove/" + product.id, { method: "POST" });
	
	if(response.ok)
		document.location.reload();
};

const resetCart = () => {
	document.location.replace("/cart/reset");
};

const saveCart = async () => {
	const entries = [];
	
	products.forEach(product => {
		const input = document.getElementById("in-cart-" + product.id);
		entries.push({ productId: product.id, amount: input.value });
	});
	
	setTimeout(() => saveAllEntries(entries), 1000);
};

const saveAllEntries = async (entries) => {
	const response = await fetch("/api/cart/save", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(entries)
	});
	
	const res = await response.json();
	
	if(res.status == 201)
		document.location.reload();
	
	else throw new Error(res.message);
};

getCartSize();