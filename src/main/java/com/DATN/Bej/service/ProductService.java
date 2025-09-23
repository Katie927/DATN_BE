package com.DATN.Bej.service;

import com.DATN.Bej.dto.request.productRequest.ProductAttributeRequest;
import com.DATN.Bej.dto.request.productRequest.ProductRequest;
import com.DATN.Bej.dto.request.productRequest.ProductVariantRequest;
import com.DATN.Bej.dto.response.productResponse.ProductListResponse;
import com.DATN.Bej.dto.response.productResponse.ProductResponse;
import com.DATN.Bej.entity.product.Product;
import com.DATN.Bej.entity.product.ProductAttribute;
import com.DATN.Bej.entity.product.ProductImage;
import com.DATN.Bej.entity.product.ProductVariant;
import com.DATN.Bej.exception.AppException;
import com.DATN.Bej.exception.ErrorCode;
import com.DATN.Bej.mapper.ProductAttributeMapper;
import com.DATN.Bej.mapper.ProductMapper;
import com.DATN.Bej.mapper.ProductVariantMapper;
import com.DATN.Bej.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class ProductService {

    ProductRepository productRepository;

    ProductMapper productMapper;
    ProductVariantMapper productVariantMapper;
    ProductAttributeMapper productAttributeMapper;

//    private final UserRepository userRepository;

    //    @PreAuthorize((has))
    public List<ProductListResponse> getProducts(){
        return productRepository.findByStatusOrderByCreateDateDesc(1).stream().map(productMapper::toProductListResponse).toList();
    }


//    admin service
    // admin get
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProductListResponse> getAllProducts(){
        return productRepository.findAllByOrderByCreateDateDesc().stream().map(productMapper::toProductListResponse).toList();
    }
    // get 1
    public ProductResponse getProductDetails(String productId){
//        System.out.println(productId);
        return productMapper.toProductResponse(productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found!")));
    }

    // add new
    public ProductResponse addNewProduct(ProductRequest request) throws IOException {
        if(productRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Product product = productMapper.toProduct(request);
        product.setCreateDate(LocalDate.now());
        System.out.println(product.getName());

        if (request.getImage() != null) {
            String image = saveFile(request.getImage());
            product.setImage(image);
        }
        if(request.getIntroImages() != null){
            product.setIntroImages(mpIntroImages(request.getIntroImages(), product));
        }
        if (request.getVariants() != null){
            List<ProductVariant> variants = mpVariants(request.getVariants(), product);
            product.setVariants(variants);
        }

        return productMapper.toProductResponse(productRepository.save(product));
    }
// add new ----------------------------------------------------------------------------------------

// update new ----------------------------------------------------------------------------------------
    public ProductResponse updateProduct(String productId, ProductRequest request) throws IOException {

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        productMapper.updateProduct(product, request);

        System.out.println("update");

        if(request.getImage() != null){
            String image = saveFile(request.getImage());
            product.setImage(image);
        }
        if(request.getVariants() != null){
            List<ProductVariant> variants = mpVariants(request.getVariants(), product);
            product.setVariants(variants);
        }
        return productMapper.toProductResponse(productRepository.save(product));
    }
// update new ----------------------------------------------------------------------------------------
    //delete
    public void delete(String productId){
        productRepository.deleteById(productId);
    }
    //set status
    public void inactive(String productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        int status = product.getStatus();
        if(status == 1) product.setStatus(0);
        else product.setStatus(1);
        productRepository.save(product);
    }

//    mapping
    // variants
    private List<ProductVariant> mpVariants(List<ProductVariantRequest> variantRequests, Product product){
        return variantRequests.stream()
                .map(req -> {
                    ProductVariant variant = productVariantMapper.toVariant(req);

                    variant.setProduct(product);
                    variant.setColor(req.getColor());
//                    variant.setOriginalPrice(req.getOriginalPrice());
//                    variant.setFinalPrice(req.getFinalPrice());

                    if(req.getDetailImages() != null){
                        System.out.println("list images!");
                        variant.setDetailImages(mpDetailImages(req.getDetailImages(), variant));
                    }
                    if(req.getAttributes() != null){
                        variant.setAttributes(mpAttributes(req.getAttributes(), variant));
                    }
                    return variant;
                }).toList();
    }
    // images
    private ProductImage mpImage(MultipartFile file){
        ProductImage img = new ProductImage();
        try {
            img.setUrl(saveFile(file));
        } catch (IOException e) {
            throw new RuntimeException("Lỗi lưu ảnh!", e);
        }
        return img;
    }
    private List<ProductImage> mpDetailImages(List<MultipartFile> files, ProductVariant variant){
        return files.stream()
                .map(file -> {
                    ProductImage img = mpImage(file);
                    img.setVariant(variant);
                    return img;
                }).toList();
    }
    private List<ProductImage> mpIntroImages(List<MultipartFile> files, Product product){
        return files.stream()
                .map(file -> {
                    ProductImage img = mpImage(file);
                    img.setProduct(product);
                    return img;
                }).toList();
    }
    // attributes
    private List<ProductAttribute> mpAttributes(List<ProductAttributeRequest> attributesReq, ProductVariant variant){
        return attributesReq.stream()
                .map(req -> {
                    ProductAttribute attribute = productAttributeMapper.toProductAttribute(req);
                    attribute.setVariant(variant);
                    attribute.setName(req.getName());
                    attribute.setOriginalPrice(req.getOriginalPrice());
                    attribute.setFinalPrice(req.getFinalPrice());
                    return attribute;
                }).toList();
    }

//    save file
//    String saveFile(MultipartFile file) throws IOException {
//        String uploadDir = "D:/Spring/newVuePr/pimg/";
//        Path path = Paths.get(uploadDir + file.getOriginalFilename());
//        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//        return uploadDir + file.getOriginalFilename();
//    }

    private String saveFile(MultipartFile file) throws IOException {
//        String uploadDir = "D:/Spring/newVuePr/BEJ/src/main/resources/static/images";
        String uploadDir = "D:/Spring/newVuePr/pimg/";
        String filename = file.getOriginalFilename();
        Path path = Paths.get(uploadDir + "/" + filename);
        log.info("adu " + String.valueOf(path));
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return  "http://localhost:8080/bej3/images/" + filename;  // Trả về URL lưu trong DB

//        return  "https://btn-bej3-api.onrender.com/bej3/images/" + filename;
    }
}
