package com.example.projetocafeteria.model;

public enum StatusPedido {
    PENDENTE, APROVADO, ENVIADO, FINALIZADO, CANCELADO;

    public static String getStatusPedido(StatusPedido status) {
        String statusPedido;

        switch (status) {
            case PENDENTE:
                statusPedido = "Pendente";
                break;
            case APROVADO:
                statusPedido = "Aprovado";
                break;
            case ENVIADO:
                statusPedido = "Enviado";
                break;
            case FINALIZADO:
                statusPedido = "Finalizado";
                break;
            default:
                statusPedido = "Cancelado";
                break;
        }
        return statusPedido;
    }
}
