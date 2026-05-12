package be.abdullah.store.services;

import be.abdullah.store.dtos.CartDto;
import be.abdullah.store.dtos.CartItemDto;
import be.abdullah.store.entities.Cart;
import be.abdullah.store.exceptions.CartNotFoundException;
import be.abdullah.store.exceptions.ProductNotFoundException;
import be.abdullah.store.mappers.CartMapper;
import be.abdullah.store.repositories.CartRepository;
import be.abdullah.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;


    public CartDto createCart() {
        var cart = new Cart();

        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId) {
        var cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        var product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        var cartItem = cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        return cartMapper.toDto(cart);
    }

    public CartItemDto updateItem(UUID cartId, Long productId, Integer quantity) {
        var cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        var cartItem = cart.getItem(productId);
        if (cartItem == null) throw new ProductNotFoundException();


        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }


    public void removeItem(UUID cartId, Long productId) {
        var cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        cart.clear();
        cartRepository.deleteById(cartId);
    }
}
