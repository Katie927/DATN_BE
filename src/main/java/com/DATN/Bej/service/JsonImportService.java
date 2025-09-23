//package com.BEJ.Bej.service;
//
//import com.BEJ.Bej.entity.product.Product;
//import com.BEJ.Bej.entity.product.ProductAttribute;
//import com.BEJ.Bej.repository.ProductRepository;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.annotation.PostConstruct;
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class JsonImportService {
//    private final ObjectMapper objectMapper;
//    private final ProductRepository productRepository;
//
//    public JsonImportService(ObjectMapper objectMapper, ProductRepository productRepository) {
//        this.objectMapper = objectMapper;
//        this.productRepository = productRepository;
//    }
//
//    @PostConstruct
//    @Transactional
//    public void importJsonData() {
//        try {
//            File file = new File("D:/Python3/New folder/hoanghamobile_selenium_updated.json");
//
//            // Đọc JSON thành danh sách DTO
//            List<ProductDTO> productDTOs = objectMapper.readValue(file, new TypeReference<>() {});
//
//            List<Product> products = new ArrayList<>();
//
//            for (ProductDTO dto : productDTOs) {
//                Product product = new Product();
//                product.setName(dto.getName());
//                product.setImage(dto.getImage());
//                product.setOriginalPrice(dto.getOriginalPrice());
//                product.setDiscount(dto.getDiscount());
//                product.setFinalPrice(dto.getFinalPrice());
//
//                // Chuyển đổi specs thành danh sách ProductAttribute
//                List<ProductAttribute> attributes = new ArrayList<>();
//                if (dto.getSpecs() != null) {
//                    for (String spec : dto.getSpecs()) {
//                        attributes.add(new ProductAttribute("Specification", spec, product));
//                    }
//                }
//                product.setAttributes(attributes);
//
//                // Chuyển đổi createDate
//                if (dto.getCreateDate() != null) {
//                    product.setCreateDate(LocalDate.parse(dto.getCreateDate()));
//                }
//
//                products.add(product);
//            }
//
//            // Lưu danh sách sản phẩm vào database
//            productRepository.saveAll(products);
//
//            System.out.println("Import dữ liệu thành công! Số lượng sản phẩm: " + products.size());
//        } catch (IOException e) {
//            System.err.println("Lỗi khi đọc file JSON: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
