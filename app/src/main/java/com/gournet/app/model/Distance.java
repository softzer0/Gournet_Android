package com.gournet.app.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Distance implements Serializable
    {

        @SerializedName("value")
        private Double value;

        @SerializedName("unit")
        private String unit;


        public Distance() {
        }


        public Distance(Double value, String unit) {
            super();
            this.value = value;
            this.unit = unit;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

    }
