package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Product;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class CartDto {

    @Size(min = 21)
    String sessionId;

    List<Product> cart = new ArrayList<>();



}
