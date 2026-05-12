package be.abdullah.store.mappers;

import be.abdullah.store.dtos.ProductDto;
import be.abdullah.store.dtos.RegisterProductRequest;
import be.abdullah.store.dtos.UpdateProductRequest;
import be.abdullah.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product product);
    Product toEntity(RegisterProductRequest productDto);

    @Mapping(target = "id" , ignore = true)
    void updateProduct(UpdateProductRequest request, @MappingTarget Product product);
}
