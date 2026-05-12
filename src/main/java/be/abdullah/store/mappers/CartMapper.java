package be.abdullah.store.mappers;

import be.abdullah.store.dtos.CartDto;
import be.abdullah.store.dtos.CartItemDto;
import be.abdullah.store.entities.Cart;
import be.abdullah.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
