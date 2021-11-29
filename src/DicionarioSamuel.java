import java.io.*;
import java.util.Locale;

public class DicionarioSamuel {

  public static void main(String[] args) throws IOException {

    //serve para chamar o arquivo entrada txt.
    FileReader entrada = new FileReader("entrada.txt");
    BufferedReader leitor = new BufferedReader(entrada);

    //serve para chamar o arquivo de saída txt.
    FileWriter saida = new FileWriter("saida.txt");
    PrintWriter escritor = new PrintWriter(saida);

    String[] entradaPalavras = preencheeVetor(leitor); //serve de "vetor base" para receber as palavras de arquivo txt.
    String[] dicionario = new String[100];//serve de "vetor definitivo", é o vetor onde armazena as palavras em ordem
    //alfabética decrescente.

    insercao(entradaPalavras, dicionario, 0);

    imprimirVetor(dicionario, escritor, 0);
    escritor.close();

  }

  public static String[] preencheeVetor(BufferedReader leitor) throws IOException {
    int indice = 0;
    String[] vetor = new String[100];
    boolean continuar = true;

    //serve para preencher o vetor base
    while (leitor.ready() && continuar) {
      String linha = leitor.readLine();
      String[] palavras = linha.split(" ");
      for (String palavra : palavras) {
        if(palavra.equals(".")){
          continuar = false;
          break;
        }else if (!palavra.equals("")) {
          vetor[indice] = palavra.toLowerCase(Locale.ROOT);
          indice++;
        }
      }
    }
    leitor.close();

    String[] retorno = new String[indice];

    for(int i=0; i<retorno.length; i++){
      retorno[i] = vetor[i];
    }

    //formatarVetor(retorno);

    return retorno;
  }

  //serve para realizar a inserção das palavras do vetor base no vetor definitivo.
  public static void insercao(String[] entrada, String[] dicionario, int i){

    if(buscaBinaria(dicionario, entrada[i], 0, dicionario.length)==0){
      int posicao = pesquisarPosicao(dicionario, entrada[i], 0);
      inserirDecrescente(dicionario, entrada[i],posicao);
    }
    //compara para saber se ainda deve permanecer na recursividade.
    if(i<entrada.length-1){
      insercao(entrada, dicionario, i+1);
    }

  }

  //serve para pesquisar a posição em que a palavra deve ser inserida no vetor definitivo de modo que fique em ordem
  //alfabética decrescente.
  public static int pesquisarPosicao(String[] dicionario, String ent, int cont){

    if(dicionario[cont] != null && ent.compareTo(dicionario[cont]) < 0)
    {
      return pesquisarPosicao(dicionario, ent, cont+1);
    }
    else
      return cont;

  }

  //após saber a posição correta onde deve ser inseridas as palavras no vetor definitivo, esse método insere a palavra
  //e vai mudando a cada palavra, uma posição para frente de cada vez.
  public static void inserirDecrescente(String[] dicionario, String ent, int pos){

    String aux = dicionario[pos];

    dicionario[pos]=ent;

    if(pos < dicionario.length-1) {
      inserirDecrescente(dicionario, aux, pos+1);
    }
  }

  //realiza a busca binária no vetor para dizer se a palavra já existe nele e se pode ser inserida.
  public static int buscaBinaria(String[] dicionario, String ent, int i, int f) {

    int m = ((i + f) / 2);

    //a palavra não está presente no vetor.
    if (i > f) {
      return 0;
    }

    //a palavra está presente no vetor.
    if (dicionario[m] != null && ent.equals(dicionario[m])) {
      return 1;
      //a palavra está a direita (ela é menor que a palavra da posição m)
    } else if (dicionario[m] != null && ent.compareTo(dicionario[m]) < 0) {
      return buscaBinaria(dicionario, ent, m+1, f);
      //a palavra está a esquerda (ela é maior que a palavra da posição m)
    } else {
      return buscaBinaria(dicionario, ent, i, m-1);
    }
  }

  //serve para gravar a saida no arquivo txt de saida.
  public static void imprimirVetor(String[] dicionario, PrintWriter escritor, int contador) {


    escritor.println(dicionario[contador]);
    System.out.println(dicionario[contador]);

    if (dicionario[contador + 1] != null) {
      imprimirVetor(dicionario, escritor, contador + 1);
    } else {
      escritor.print("Total de palavras diferentes no dicionário é de: " + (contador + 1));
      System.out.print("Total de palavras diferentes no dicionário é de: " + (contador + 1));
    }
  }
}
