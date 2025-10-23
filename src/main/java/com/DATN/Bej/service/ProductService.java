package com.DATN.Bej.service;

import com.DATN.Bej.dto.request.productRequest.ProductAttributeRequest;
import com.DATN.Bej.dto.request.productRequest.ProductImageRequest;
import com.DATN.Bej.dto.request.productRequest.ProductRequest;
import com.DATN.Bej.dto.request.productRequest.ProductVariantRequest;
import com.DATN.Bej.dto.response.productResponse.ProductListResponse;
import com.DATN.Bej.dto.response.productResponse.ProductResponse;
import com.DATN.Bej.entity.product.*;
import com.DATN.Bej.exception.AppException;
import com.DATN.Bej.exception.ErrorCode;
import com.DATN.Bej.mapper.product.CategoryMapper;
import com.DATN.Bej.mapper.product.ProductAttributeMapper;
import com.DATN.Bej.mapper.product.ProductMapper;
import com.DATN.Bej.mapper.product.ProductVariantMapper;
import com.DATN.Bej.repository.product.CategoryRepository;
import com.DATN.Bej.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    ProductMapper productMapper;
    CategoryMapper categoryMapper;
    ProductVariantMapper productVariantMapper;
    ProductAttributeMapper productAttributeMapper;

//    private final UserRepository userRepository;

    //    @PreAuthorize((has))
    public List<ProductListResponse> getProducts(){
        return productRepository.findByStatusOrderByCreateDateDesc(1).stream().map(productMapper::toProductListResponse).toList();
    }


    //    admin service
    // admin get
//    @PreAuthorize("hasRole('ADMIN')")
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
        System.out.println("product add");
        if(productRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Product product = productMapper.toProduct(request);

        Category category = categoryRepository.findById(request.getCategory().getId()).orElseThrow(
                () -> new AppException(ErrorCode.ROLE_NOT_FOUND)
        );
        System.out.println("category: " + category.getId());
        product.setCategory(category);
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
        System.out.println("=== PRODUCT UPDATE DEBUG ===");
        System.out.println("Product ID: " + product.getId());
        System.out.println("IntroImages: ");
        product.getIntroImages().forEach(img ->
                System.out.println(" - id=" + img.getId() + ", url=" + img.getUrl()));

        System.out.println("Variants: ");
        product.getVariants().forEach(variant -> {
            System.out.println(" - Variant id=" + variant.getId() + ", color=" + variant.getColor());

            System.out.println("   DetailImages:");
            variant.getDetailImages().forEach(img ->
                    System.out.println("     * id=" + img.getId() + ", url=" + img.getUrl()));

            System.out.println("   Attributes:");
            variant.getAttributes().forEach(attr ->
                    System.out.println("     * id=" + attr.getId() + ", name=" + attr.getName()));
        });
        System.out.println("update");


        return productMapper.toProductResponse(productRepository.save(product));
    }
// add new ----------------------------------------------------------------------------------------

    // update new ----------------------------------------------------------------------------------------
    @Transactional
    public ProductResponse updateProduct(String productId, ProductRequest request) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(
                ()  -> new AppException(ErrorCode.USER_NOT_EXISTED));

        productMapper.updateProduct(product, request);
        Category category = categoryRepository.findById(request.getCategory().getId()).orElseThrow(
                () -> new AppException(ErrorCode.ROLE_NOT_FOUND)
        );
        System.out.println("category: " + category.getId());
        product.setCategory(category);
        System.out.println(product.getCategory().getName());
        // cập nhật ảnh đại diện
        if (request.getImage() != null) {
            String image = saveFile(request.getImage());
            product.setImage(image);
        }

        // intro images
        if (request.getIntroImages() != null) {
            Map<String, ProductImage> oldImages = product.getIntroImages().stream()
                    .filter(img -> img.getId() != null)
                    .collect(Collectors.toMap(ProductImage::getId, Function.identity()));

            List<ProductImage> updatedImages = new ArrayList<>();
            for (ProductImageRequest reqImg : request.getIntroImages()) {
                if (reqImg.getId() != null && oldImages.containsKey(reqImg.getId())) {
                    ProductImage img = oldImages.get(reqImg.getId());
                    if (reqImg.getFile() != null) {
                        img.setUrl(saveFile(reqImg.getFile()));
                    }
                    updatedImages.add(img);
                } else {
                    ProductImage newImg = mpImage(reqImg.getFile());
                    newImg.setProduct(product);
                    updatedImages.add(newImg);
                }
            }
            // dùng clear + addAll thay cho setIntroImages
            product.getIntroImages().clear();
            product.getIntroImages().addAll(updatedImages);
        }

        // variants
        if (request.getVariants() != null) {
            Map<String, ProductVariant> oldVariants = product.getVariants().stream()
                    .filter(v -> v.getId() != null)
                    .collect(Collectors.toMap(ProductVariant::getId, Function.identity()));

            List<ProductVariant> updatedVariants = new ArrayList<>();
            for (ProductVariantRequest reqVar : request.getVariants()) {
                if (reqVar.getId() != null && oldVariants.containsKey(reqVar.getId())) {
                    ProductVariant variant = oldVariants.get(reqVar.getId());
                    variant.setColor(reqVar.getColor());

                    // detail images
                    if (reqVar.getDetailImages() != null) {
                        Map<String, ProductImage> oldDetailImgs = variant.getDetailImages().stream()
                                .filter(img -> img.getId() != null)
                                .collect(Collectors.toMap(ProductImage::getId, Function.identity()));

                        List<ProductImage> newDetailImgs = new ArrayList<>();
                        for (ProductImageRequest imgReq : reqVar.getDetailImages()) {
                            if (imgReq.getId() != null && oldDetailImgs.containsKey(imgReq.getId())) {
                                ProductImage img = oldDetailImgs.get(imgReq.getId());
                                if (imgReq.getFile() != null) {
                                    img.setUrl(saveFile(imgReq.getFile()));
                                }
                                newDetailImgs.add(img);
                            } else {
                                ProductImage img = mpImage(imgReq.getFile());
                                img.setVariant(variant);
                                newDetailImgs.add(img);
                            }
                        }
                        variant.getDetailImages().clear();
                        variant.getDetailImages().addAll(newDetailImgs);
                    }

                    // attributes
                    if (reqVar.getAttributes() != null) {
                        Map<String, ProductAttribute> oldAttrs = variant.getAttributes().stream()
                                .filter(a -> a.getId() != null)
                                .collect(Collectors.toMap(ProductAttribute::getId, Function.identity()));

                        List<ProductAttribute> newAttrs = new ArrayList<>();
                        for (ProductAttributeRequest attrReq : reqVar.getAttributes()) {
                            if (attrReq.getId() != null && oldAttrs.containsKey(attrReq.getId())) {
                                ProductAttribute attr = oldAttrs.get(attrReq.getId());
                                attr.setName(attrReq.getName());
                                attr.setOriginalPrice(attrReq.getOriginalPrice());
                                attr.setFinalPrice(attrReq.getFinalPrice());
                                newAttrs.add(attr);
                            } else {
                                ProductAttribute attr = productAttributeMapper.toProductAttribute(attrReq);
                                attr.setVariant(variant);
                                newAttrs.add(attr);
                            }
                        }
                        variant.getAttributes().clear();
                        variant.getAttributes().addAll(newAttrs);
                    }
                    updatedVariants.add(variant);
                } else {
                    // thêm variant mới
                    ProductVariant newVariant = productVariantMapper.toVariant(reqVar);
                    newVariant.setProduct(product);

                    if (reqVar.getDetailImages() != null) {
                        newVariant.setDetailImages(mpDetailImages(reqVar.getDetailImages(), newVariant));
                    }
                    if (reqVar.getAttributes() != null) {
                        newVariant.setAttributes(mpAttributes(reqVar.getAttributes(), newVariant));
                    }
                    updatedVariants.add(newVariant);
                }
            }

            product.getVariants().clear();
            product.getVariants().addAll(updatedVariants);
        }

        System.out.println("update");

        System.out.println("=== PRODUCT UPDATE DEBUG ===");
        System.out.println("Product ID: " + product.getId());
        System.out.println("IntroImages: ");
        product.getIntroImages().forEach(img ->
                System.out.println(" - id=" + img.getId() + ", url=" + img.getUrl()));

        System.out.println("Variants: ");
        product.getVariants().forEach(variant -> {
            System.out.println(" - Variant id=" + variant.getId() + ", color=" + variant.getColor());

            System.out.println("   DetailImages:");
            variant.getDetailImages().forEach(img ->
                    System.out.println("     * id=" + img.getId() + ", url=" + img.getUrl()));

            System.out.println("   Attributes:");
            variant.getAttributes().forEach(attr ->
                    System.out.println("     * id=" + attr.getId() + ", name=" + attr.getName()));
        });

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
    private List<ProductImage> mpDetailImages(List<ProductImageRequest> files, ProductVariant variant){
        return files.stream()
                .map(file -> {
                    ProductImage img = mpImage(file.getFile());
                    img.setVariant(variant);
                    return img;
                }).toList();
    }
    private List<ProductImage> mpIntroImages(List<ProductImageRequest> files, Product product){
        return files.stream()
                .map(file -> {
                    ProductImage img = mpImage(file.getFile());
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
//        log.info("adu " + String.valueOf(path));
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return  "http://localhost:8080/bej3/images/" + filename;  // Trả về URL lưu trong DB

//        return  "https://btn-bej3-api.onrender.com/bej3/images/" + filename;
    }
}
