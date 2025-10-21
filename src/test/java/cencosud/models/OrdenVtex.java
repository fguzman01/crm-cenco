package cencosud.models;

public class OrdenVtex {
    
        private String compraId;
        private String boleta;
        private String rut;
        private String monto;
        private String fecha;
        private String paymentMethod;
        private String purchaseStatus;
        private String email;
        private String app;
        private String deliveryType;
        private String salesChannel;
        private String tienda;
        private String ordenStatus;
        private String fechaBoleta;
        private String validaBoleta;
        private String tipoPedido;

        public String getTipoPedido() {
            return tipoPedido;
        }
        public void setTipoPedido(String tipoPedido) {
            this.tipoPedido = tipoPedido;
        }
        public String getFechaBoleta() {
            return fechaBoleta;
        }
        public void setFechaBoleta(String fechaBoleta) {
            this.fechaBoleta = fechaBoleta;
        }
        public String getValidaBoleta() {
            return validaBoleta;
        }
        public void setValidaBoleta(String validaBoleta) {
            this.validaBoleta = validaBoleta;
        }

        public String getBoleta() {
            return boleta;
        }
        public void setBoleta(String boleta) {
            this.boleta = boleta;
        }
        
        public String getRut() {
            return rut;
        }
        public void setRut(String rut) {
            this.rut = rut;
        }
        
        public String getOrdenStatus() {
            return ordenStatus;
        }
        public void setOrdenStatus(String ordenStatus) {
            this.ordenStatus = ordenStatus;
        }
        public String getTienda() {
            return tienda;
        }
        public void setTienda(String tienda) {
            this.tienda = tienda;
        }
        public String getSalesChannel() {
            return salesChannel;
        }
        public void setSalesChannel(String salesChannel) {
            this.salesChannel = salesChannel;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getApp() {
            return app;
        }
        public void setApp(String app) {
            this.app = app;
        }
        public String getDeliveryType() {
            return deliveryType;
        }
        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }
        public String getCompraId() {
            return compraId;
        }
        public void setCompraId(String compraId) {
            this.compraId = compraId;
        }
        public String getMonto() {
            return monto;
        }
        public void setMonto(String monto) {
            this.monto = monto;
        }
        public String getFecha() {
            return fecha;
        }
        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
        public String getPaymentMethod() {
            return paymentMethod;
        }
        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }
        public String getPurchaseStatus() {
            return purchaseStatus;
        }
        public void setPurchaseStatus(String purchaseStatus) {
            this.purchaseStatus = purchaseStatus;
        }
        @Override
        public String toString() {
            return "OrdenVtex [compraId=" + compraId + ", boleta=" + boleta + ", rut=" + rut + ", monto=" + monto
                    + ", fecha=" + fecha + ", paymentMethod=" + paymentMethod + ", purchaseStatus=" + purchaseStatus
                    + ", email=" + email + ", app=" + app + ", deliveryType=" + deliveryType + ", salesChannel="
                    + salesChannel + ", tienda=" + tienda + ", ordenStatus=" + ordenStatus + ", fechaBoleta="
                    + fechaBoleta + ", validaBoleta=" + validaBoleta + ", tipoPedido=" + tipoPedido + "]";
        }
       
        
        
        
        
}
