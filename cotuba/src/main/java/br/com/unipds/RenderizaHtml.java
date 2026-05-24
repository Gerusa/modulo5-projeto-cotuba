package br.com.unipds;

import org.commonmark.node.Node;
import org.commonmark.renderer.html.HtmlRenderer;

public class RenderizaHtml {

  public static String render(Node document){
    HtmlRenderer renderer = HtmlRenderer.builder().build();
    return renderer.render(document);
  }
}
