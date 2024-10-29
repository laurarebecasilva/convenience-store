package com.api.rest.conveniencestore.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class CsvReportGenerator {

    private static final String REPORT_DIRECTORY = "reports"; // Diretório
    private static final String SEPARATOR = "-";
    public static final String EXTENSION = ".csv";

    /**
     * Gera um arquivo CSV para os dados fornecidos.
     *
     * @param fileName Nome do arquivo de relatório (sem extensão)
     * @param headers Cabeçalhos das colunas do CSV
     * @param data Lista de dados a serem incluídos no relatório
     * @param rowMapper Função para mapear cada item da lista para uma linha CSV formatada
     * @param <T> Tipo de dados na lista
     */
    public static <T> void generateCsvReport(String fileName, String[] headers, List<T> data, Function<T, String> rowMapper) {
        // Verifica e cria o diretório se necessário
        File directory = new File(REPORT_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String timestamp = DateUtil.formateDateCsv(LocalDateTime.now());
        // Adiciona a extensão CSV ao nome do arquivo e define o caminho completo
        String filePath = REPORT_DIRECTORY + File.separator + fileName + SEPARATOR + timestamp + EXTENSION;

        try (FileWriter writer = new FileWriter(filePath)) {
            // Escreve os cabeçalhos
            writer.write(String.join(",", headers) + "\n");
            // Escreve os dados usando o mapeador de linhas
            for (T item : data) {
                writer.write(rowMapper.apply(item) + "\n");
            }

            System.out.println("Relatório gerado com sucesso em: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
