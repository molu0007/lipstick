package com.qyy.app.lipstick.model.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dengwg
 * @date 2018/3/16
 */
public class PackageList implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;

    private int idIfcFlowCardProduct;

    private String prdName;

    private BigDecimal prdPrice;

    private String type;


    public int getIdIfcFlowCardProduct() {
        return idIfcFlowCardProduct;
    }

    public void setIdIfcFlowCardProduct(int idIfcFlowCardProduct) {
        this.idIfcFlowCardProduct = idIfcFlowCardProduct;
    }


    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public BigDecimal getPrdPrice() {
        return prdPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
