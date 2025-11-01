if(product)
	document.getElementById("button-add-to-cart-" + product.id).addEventListener("click", () => addToCart(product));