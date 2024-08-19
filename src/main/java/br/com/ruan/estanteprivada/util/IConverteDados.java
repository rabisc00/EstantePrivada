package br.com.ruan.estanteprivada.util;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}