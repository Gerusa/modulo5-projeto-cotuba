package br.com.unipds;

import org.apache.commons.cli.CommandLine;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;

public class Main {

    public static final String PDF = "pdf";
    public static final String EPUB = "epub";

    void main(String[] args) {
        int exitCode = executar(args);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    int executar(String[] args) {

        CommandLine cmd = ValidaUtil.getCommandLine(args);
        if (Objects.isNull(cmd)){
            return 1;
        }

        Path diretorioDosMD;
        String formato;
        Path arquivoDeSaida;
        boolean modoVerboso = false;

        try {

            String nomeDoDiretorioDosMD = cmd.getOptionValue(getDir());

            if (nomeDoDiretorioDosMD != null) {
                diretorioDosMD = Paths.get(nomeDoDiretorioDosMD);
                if (!Files.isDirectory(diretorioDosMD)) {
                    throw new IllegalArgumentException(nomeDoDiretorioDosMD + " não é um diretório.");
                }
            } else {
                Path diretorioAtual = Paths.get("");
                diretorioDosMD = diretorioAtual;
            }

            String nomeDoFormatoDoEbook = cmd.getOptionValue(getFormat());

            if (nomeDoFormatoDoEbook != null) {
                formato = nomeDoFormatoDoEbook.toLowerCase();
            } else {
                formato = PDF;
            }

            String nomeDoArquivoDeSaidaDoEbook = cmd.getOptionValue(getOutput());
            if (nomeDoArquivoDeSaidaDoEbook != null) {
                arquivoDeSaida = Paths.get(nomeDoArquivoDeSaidaDoEbook);
            } else {
                arquivoDeSaida = Paths.get("book." + formato.toLowerCase());
            }
            if (Files.isDirectory(arquivoDeSaida)) {
                // deleta arquivos do diretório recursivamente
                Files.walk(arquivoDeSaida).sorted(Comparator.reverseOrder())
                        .map(Path::toFile).forEach(File::delete);
            } else {
                Files.deleteIfExists(arquivoDeSaida);
            }

            modoVerboso = cmd.hasOption("verbose");

            if (PDF.equals(formato)) {
                GeraPdf.gerar(arquivoDeSaida, diretorioDosMD);

            } else if (EPUB.equals(formato)) {
                GeraEpub.gerar(arquivoDeSaida, diretorioDosMD);

            } else {
                throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
            }

            System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);
            return 0;

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (modoVerboso) {
                System.err.println();
                ex.printStackTrace();
            }
            return 1;
        }
    }

    private static String getOutput() {
        return "output";
    }

    private static String getFormat() {
        return "format";
    }

    private static String getDir() {
        return "dir";
    }

}
