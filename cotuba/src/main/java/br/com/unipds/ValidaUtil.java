package br.com.unipds;

import org.apache.commons.cli.*;

public class ValidaUtil {

  private ValidaUtil(){

  }

  public static CommandLine getCommandLine(String[] args){

    var options = new Options();

    var opcaoDeDiretorioDosMD = new Option("d", "dir", true,
        "Diretório que contém os arquivos md. Default: diretório atual.");
    options.addOption(opcaoDeDiretorioDosMD);

    var opcaoDeFormatoDoEbook = new Option("f", "format", true,
        "Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf");
    options.addOption(opcaoDeFormatoDoEbook);

    var opcaoDeArquivoDeSaida = new Option("o", "output", true,
        "Arquivo de saída do ebook. Default: book.{formato}.");
    options.addOption(opcaoDeArquivoDeSaida);

    var opcaoModoVerboso = new Option("v", "verbose", false,
        "Habilita modo verboso.");
    options.addOption(opcaoModoVerboso);

    CommandLineParser cmdParser = new DefaultParser();
    var ajuda = new HelpFormatter();

    try {
      return cmdParser.parse(options, args);
    } catch (ParseException e) {
      System.err.println(e.getMessage());
      ajuda.printHelp("cotuba", options);
      return null;
    }

  }
}
