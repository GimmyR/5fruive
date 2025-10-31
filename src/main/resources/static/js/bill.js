const savePDF = () => {
	var pdf = new jsPDF();
	
	pdf.fromHTML(document.getElementById("my-bill"), 15, 15, {
		"width": 170,
	});
	
	pdf.save("my-bill.pdf");
}

document.getElementById("btn-save-pdf").addEventListener("click", () => savePDF());