package com.project.WebsiteBanDienThoai.service;

import com.project.WebsiteBanDienThoai.model.CartItem;
import com.project.WebsiteBanDienThoai.model.Product;
import com.project.WebsiteBanDienThoai.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class CartService {
    private List<CartItem> cartItems = new ArrayList<>();
    @Autowired
    private ProductRepository productRepository;
    public String addToCart(Long productId, int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        // Check if the product exists in the database
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // Calculate current quantity in cart for this product
            int currentQuantityInCart = 0;
            for (CartItem item : cartItems) {
                if (item.getProduct().getId().equals(productId)) {
                    currentQuantityInCart += item.getQuantity();
                }
            }

            // Calculate total quantity to be added (including current quantity in cart)
            int totalQuantityToAdd = currentQuantityInCart + quantity;

            // Check if total quantity to add exceeds available stock
            if (totalQuantityToAdd > product.getStock()) {
                return "Quantity exceeds available stock for product: " + product.getName();
            }

            // Check if the quantity to add is valid (not negative and not zero)
            if (quantity <= 0) {
                return "Invalid quantity: " + quantity;
            }

            // Check if the product already exists in the cart
            boolean found = false;
            for (CartItem item : cartItems) {
                if (item.getProduct().getId().equals(productId)) {
                    // If product already exists, update the quantity
                    item.setQuantity(item.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }

            // If product does not exist in the cart, add it
            if (!found) {
                cartItems.add(new CartItem(product, quantity));
            }

            return null; // Return null to indicate success (no error)
        } else {
            // Return error message if product is not found
            return "Product not found with ID: " + productId;
        }
    }
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public void removeFromCart(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }
    public void clearCart() {
        cartItems.clear();
    }
}