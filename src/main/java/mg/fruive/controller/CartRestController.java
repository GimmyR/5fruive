package mg.fruive.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mg.fruive.exception.NotFoundException;
import mg.fruive.record.CartEntryForm;
import mg.fruive.record.FruiveResponse;
import mg.fruive.service.CartService;
import mg.fruive.service.ErrorService;

@RestController
@AllArgsConstructor
public class CartRestController {
	
	private CartService cartService;
	private ErrorService errorService;
	
	@GetMapping("/api/cart-size")
	public ResponseEntity<FruiveResponse> getCartSize() {
		
		FruiveResponse response = new FruiveResponse(null, cartService.getCartSize());
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping("/api/cart/add")
	public ResponseEntity<FruiveResponse> addToCart(@Valid @RequestBody CartEntryForm cart, BindingResult bindingResult) {
		
		int status = 201;
		FruiveResponse response = null;
		
		try {
			
			errorService.throwExceptionIfErrorsExist(bindingResult, true);
			response = new FruiveResponse("Product saved in cart !", cartService.addToCart(cart.productId(), cart.amount()));
			
		} catch (NotFoundException e) {
			
			status = 404;
			response = new FruiveResponse(e.getMessage(), null);
			
		} catch (Exception e) {
			
			status = 400;
			response = new FruiveResponse(e.getMessage(), null);
			
		}  return ResponseEntity.status(status).body(response);
		
	}
	
	@PostMapping("/api/cart/remove/{productId}")
	public ResponseEntity<FruiveResponse> removeProduct(@PathVariable Integer productId) {
		
		int status = 201;
		FruiveResponse response = null;
		
		try {
			
			cartService.removeProduct(productId);
			response = new FruiveResponse("Product is successfully removed from cart !", null);
			
		} catch (NotFoundException e) {
			
			status = 404;
			response = new FruiveResponse(e.getMessage(), null);
			
		} return ResponseEntity.status(status).body(response);
		
	}
	
	@PostMapping("/api/cart/save")
	public ResponseEntity<FruiveResponse> saveCart(@Valid @RequestBody List<CartEntryForm> entries, BindingResult bindingResult) {
		
		int status = 201;
		FruiveResponse response = null;
		
		try {
			
			errorService.throwExceptionIfErrorsExist(bindingResult, true);
			cartService.saveCart(entries);
			response = new FruiveResponse("Cart is successfully saved !", null);
			
		} catch (NotFoundException e) {
			
			status = 404;
			response = new FruiveResponse(e.getMessage(), null);
			
		} catch (Exception e) {
			
			status = 400;
			response = new FruiveResponse(e.getMessage(), null);
			
		}  return ResponseEntity.status(status).body(response);
		
	}

}
