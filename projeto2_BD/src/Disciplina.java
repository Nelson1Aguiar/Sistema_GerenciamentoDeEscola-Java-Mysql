public class Disciplina {
    private int codigo;
    private String nome;
    private int creditos;
    private String periodo;
    private int qtdEstudante;
    private float maiorNota;
    private float menorNota;
    private float mediaNotas;

    public int getQtdEstudante() {
        return qtdEstudante;
    }

    public void setQtdEstudante(int qtdEstudante) {
        this.qtdEstudante = qtdEstudante;
    }

    public float getMaiorNota() {
        return maiorNota;
    }

    public void setMaiorNota(float maiorNota) {
        this.maiorNota = maiorNota;
    }

    public float getMenorNota() {
        return menorNota;
    }

    public void setMenorNota(float menorNota) {
        this.menorNota = menorNota;
    }

    public float getMediaNotas() {
        return mediaNotas;
    }

    public void setMediaNotas(float mediaNotas) {
        this.mediaNotas = mediaNotas;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
