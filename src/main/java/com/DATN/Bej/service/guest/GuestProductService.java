package com.DATN.Bej.service.guest;

import com.DATN.Bej.dto.response.guest.ProductDetailRes;
import com.DATN.Bej.exception.AppException;
import com.DATN.Bej.exception.ErrorCode;
import com.DATN.Bej.mapper.product.ProductMapper;
import com.DATN.Bej.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class GuestProductService {

    ProductMapper productMapper;
    ProductRepository productRepository;

    public ProductDetailRes getProductDetails(String productId){
        return productMapper.toProductDetailResponse(productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

}
