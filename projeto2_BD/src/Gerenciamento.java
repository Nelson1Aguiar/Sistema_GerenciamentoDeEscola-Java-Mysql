import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.ResultSet;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Gerenciamento {
    public Connection conexao = null;
    public PreparedStatement pstm = null;
    public Estudante estudante=new Estudante();
    public Disciplina disciplina=new Disciplina();

    public void menu(){
        int opPrincipal,op;
        Scanner scan = new Scanner(System.in);
        System.out.println("Ola usuario! \n");
        do {
            System.out.println("------------------------------------------------------------------");
            System.out.println("Digite a opção desejada: \n1 - manipular tabela alunos\n2 - manipular tabela disciplina\n0 - sair: ");
            opPrincipal=scan.nextInt();
            if(opPrincipal==1) {
                System.out.println("------------------------------------------------------------------");
                System.out.println("Digite a opção desejada: \n1- inserir aluno\n2- atualizar aluno\n3- remover aluno\n4- listar alunos\n5- adicionar resultado\n6- Exibir historico aluno\n7- Buscar aluno");
                op = scan.nextInt();

                switch (op) {
                    case 1:
                        int ano, mes, dia;
                        String sexo;
                        scan.nextLine();
                        System.out.println("Digite o nome do estudante: ");
                        estudante.setNome(scan.nextLine());

                        System.out.println("Digite o email do estudante: ");
                        estudante.setEmail(scan.nextLine());

                        System.out.println("Digite o telefone do estudante: ");
                        estudante.setTelefone(scan.nextLine());

                        System.out.println("Digite a data de nascimento (dd/MM/yyyy): ");
                        String dataStr = scan.next();

                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

                        try {
                            estudante.setNascimento(formato.parse(dataStr));
                        } catch (ParseException e) {
                            System.out.println("Formato de data inválido.");
                        }

                        System.out.println("Digite o sexo do estudante: ");
                        sexo = scan.next();

                        if (sexo.equals("M") || sexo.equals("m") || sexo.equals("masculino") || sexo.equals("Masculino")) {
                            estudante.setSexo(true);
                        } else {
                            estudante.setSexo(false);
                        }

                        addStudent(estudante);
                        System.out.println("Estudante inserido!");
                        break;
                    case 2:
                        System.out.println("Digite a matricula do estudante que deseja atualizar: ");
                        estudante.setMatricula(scan.nextInt());
                        scan.nextLine();

                        System.out.println("Digite os novos dados do estudante!\n\n ");
                        System.out.println("Nome: ");
                        estudante.setNome(scan.nextLine());
                        System.out.println("Email: ");
                        estudante.setEmail(scan.nextLine());

                        System.out.println("Telefone: ");
                        estudante.setTelefone(scan.nextLine());

                        System.out.println("Data de nascimento (dd/MM/yyyy): ");
                        String dataStrUpdate = scan.next();

                        SimpleDateFormat formatoUpdate = new SimpleDateFormat("dd/MM/yyyy");

                        try {
                            estudante.setNascimento(formatoUpdate.parse(dataStrUpdate));
                        } catch (ParseException e) {
                            System.out.println("Formato de data inválido.");
                        }

                        System.out.println("Sexo ('M' para masculino e 'F' para feminino): ");
                        String sexoUpdate = scan.next();

                        if (sexoUpdate.equals("M") || sexoUpdate.equals("m") || sexoUpdate.equals("masculino") || sexoUpdate.equals("Masculino")) {
                            estudante.setSexo(true);
                        } else {
                            estudante.setSexo(false);
                        }

                        updateStudent(estudante);
                        System.out.println("Estudante atualizado!");
                        break;
                    case 3:
                        System.out.println("Digite a matricula que deseja remover: ");
                        estudante.setMatricula(scan.nextInt());
                        delete(estudante);
                        System.out.println("Estudante deletado!");
                        break;
                    case 4:
                        System.out.println("LISTA DE ESTUDANTES: \n");
                        for (Estudante e : getEstudantes()) {
                            SimpleDateFormat dataFormatar = new SimpleDateFormat("dd/MM/yyyy ");
                            String dataFormatada = dataFormatar.format(e.getNascimento());

                            System.out.println("Matricula: " + e.getMatricula());
                            System.out.println("Nome: " + e.getNome());
                            System.out.println("Email: " + e.getEmail());
                            System.out.println("Telefone: " + e.getTelefone());
                            System.out.println("Data Nascimento: " + dataFormatada);
                            if (e.isSexo() == true) {
                                System.out.println("Sexo: Masculino");
                            } else {
                                System.out.println("Sexo: Feminino");
                            }
                            System.out.println("\n");
                        }
                        break;
                    case 5:
                        scan.useLocale(Locale.US);
                        System.out.println("Digite a matricula do estudante: ");
                        estudante.setMatricula(scan.nextInt());

                        System.out.println("Digite o codigo da disciplina: ");
                        disciplina.setCodigo(scan.nextInt());

                        scan.nextLine();
                        System.out.println("Digite o periodo da  (Ex: 2021.1): ");
                        disciplina.setPeriodo(scan.nextLine());

                        System.out.println("Digite a nota do estudante (0-10): ");
                        estudante.setNota(scan.nextFloat());

                        System.out.println("Digite a frequencia do estudante (0-100): ");
                        estudante.setFrequencia(scan.nextFloat());

                        addEstudanteDisciplina(estudante,disciplina);
                        break;
                    case 6:
                        System.out.println("Digite a matricula do estudante: ");
                        estudante.setMatricula(scan.nextInt());
                        exibirHistorico(estudante);
                        break;
                    case 7:
                        scan.nextLine();
                        System.out.println("Digite o nome do estudante ou os caracteres: ");
                        String aux=scan.nextLine();

                        for (Estudante e : getEstudantes()) {
                            SimpleDateFormat dataFormatar = new SimpleDateFormat("dd/MM/yyyy ");
                            String dataFormatada = dataFormatar.format(e.getNascimento());
                            if (e.getNome().toLowerCase().contains(aux.toLowerCase())) {
                                System.out.println("Matricula: " + e.getMatricula());
                                System.out.println("Nome: " + e.getNome());
                                System.out.println("Email: " + e.getEmail());
                                System.out.println("Telefone: " + e.getTelefone());
                                System.out.println("Data Nascimento: " + dataFormatada);
                                if (e.isSexo() == true) {
                                    System.out.println("Sexo: Masculino");
                                } else {
                                    System.out.println("Sexo: Feminino");
                                }
                                System.out.println("\n");
                            }
                        }
                        break;
                    default:
                        System.out.println("OPCAO INVALIDA!");
                        break;
                }

                System.out.println("Pressione Enter para continuar...");
                scan.nextLine();
                scan.nextLine();
                System.out.println();
            }
            if(opPrincipal==2){
                System.out.println("------------------------------------------------------------------");
                System.out.println("Digite a opção desejada: \n1- inserir disciplina\n2- atualizar disciplina\n3- remover disciplina\n4- listar disciplinas\n5- Exibir turma");
                op = scan.nextInt();

                switch (op) {
                    case 1:
                        scan.nextLine();

                        System.out.println("Digite o nome da disciplina: ");
                        disciplina.setNome(scan.nextLine());

                        System.out.println("Digite a quantidade de creditos: ");
                        disciplina.setCreditos(scan.nextInt());

                        addDisciplina(disciplina);
                        System.out.println("Disciplina inserida!");
                        break;
                    case 2:
                        System.out.println("Digite o codigo da disciplina: ");
                        disciplina.setCodigo(scan.nextInt());
                        scan.nextLine();

                        System.out.println("Digite os novos dados!\n\n ");
                        System.out.println("Nome: ");
                        disciplina.setNome(scan.nextLine());
                        System.out.println("Creditos: ");
                        disciplina.setCreditos(scan.nextInt());

                        updateDisciplina(disciplina);
                        System.out.println("Disciplina atualizada!");
                        break;
                    case 3:
                        System.out.println("Digite o codigo da disciplina que deseja remover: ");
                        disciplina.setCodigo(scan.nextInt());
                        delete(disciplina);
                        System.out.println("Disciplina deletada!");
                        break;
                    case 4:
                        System.out.println("LISTA DE DISCIPLINAS: \n");
                        for (Disciplina d : getDisciplinas()) {
                            System.out.println("Codigo: " + d.getCodigo());
                            System.out.println("Nome: " + d.getNome());
                            System.out.println("Creditos: " + d.getCreditos());
                            System.out.println("\n");
                        }
                        break;
                    case 5:
                        System.out.println("Digite o codigo da disciplina: ");
                        disciplina.setCodigo(scan.nextInt());
                        scan.nextLine();
                        System.out.println("Digite o periodo: ");
                        disciplina.setPeriodo(scan.nextLine());

                        exibirTurma(disciplina);
                        break;
                    default:
                        System.out.println("OPCAO INVALIDA!");
                        break;
                }

                System.out.println("Pressione Enter para continuar...");
                scan.nextLine();
                scan.nextLine();
                System.out.println();
            }
            if(opPrincipal ==0){
                System.out.println("ENCERRANDO PROGRAMA...");
            }
            if(opPrincipal>2 || opPrincipal<0){
                System.out.println("OPCAO INVALIDA!");
            }


        } while (opPrincipal != 0);


    }

    public void addStudent(Estudante estudante){
        String sql = "INSERT INTO aluno (nome,email,telefone,data_nasc,sexo) VALUES (?,?,?,?,?)";
        Date dataSql = new Date(estudante.getNascimento().getTime());
        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, estudante.getNome());
            pstm.setString(2, estudante.getEmail());
            pstm.setString(3, estudante.getTelefone());
            pstm.setDate(4, dataSql);
            pstm.setBoolean(5, estudante.isSexo());
            
            pstm.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public void updateStudent(Estudante estudante){
        String sql = "UPDATE aluno SET nome=?,email=?,telefone=?,data_nasc=?,sexo=? WHERE matricula=?";
        Date dataSql = new Date(estudante.getNascimento().getTime());

        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, estudante.getNome());
            pstm.setString(2, estudante.getEmail());
            pstm.setString(3, estudante.getTelefone());
            pstm.setDate(4, dataSql);
            pstm.setBoolean(5, estudante.isSexo());
            pstm.setInt(6, estudante.getMatricula());

            pstm.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public void delete(Estudante estudante) {
        String sql = "DELETE FROM aluno WHERE matricula=?";
        Connection conexao = null;
        PreparedStatement pstm = null;

        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, estudante.getMatricula());

            pstm.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public List<Estudante> getEstudantes(){
        String sql = "SELECT * FROM aluno";
        List<Estudante> estudante = new ArrayList<Estudante>();
        ResultSet rs=null;
        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Estudante est=new Estudante();
                est.setMatricula(rs.getInt("matricula"));
                est.setNome(rs.getString("nome"));
                est.setEmail(rs.getString("email"));
                est.setTelefone(rs.getString("telefone"));
                java.sql.Date dataSql = rs.getDate("data_nasc");
                est.setSexo(rs.getBoolean("sexo"));

                java.util.Date utilDate = new java.util.Date(dataSql.getTime());
                est.setNascimento(utilDate);

                estudante.add(est);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return estudante;
    }

    public void addDisciplina(Disciplina disciplina){
        String sql = "INSERT INTO disciplina (nome,creditos) VALUES (?,?)";
        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, disciplina.getNome());
            pstm.setInt(2, disciplina.getCreditos());

            pstm.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public void updateDisciplina(Disciplina disciplina){
        String sql = "UPDATE disciplina SET nome=?,creditos=? WHERE codigo=?";

        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setString(1, disciplina.getNome());
            pstm.setInt(2, disciplina.getCreditos());
            pstm.setInt(3, disciplina.getCodigo());

            pstm.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public void delete(Disciplina disciplina) {
        String sql = "DELETE FROM disciplina WHERE codigo=?";
        Connection conexao = null;
        PreparedStatement pstm = null;

        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, disciplina.getCodigo());

            pstm.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public List<Disciplina> getDisciplinas(){
        String sql = "SELECT * FROM disciplina";
        List<Disciplina> disciplina = new ArrayList<Disciplina>();
        ResultSet rs=null;
        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Disciplina dis=new Disciplina();
                dis.setCodigo(rs.getInt("codigo"));
                dis.setNome(rs.getString("nome"));
                dis.setCreditos(rs.getInt("creditos"));

                disciplina.add(dis);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return disciplina;
    }

    public void addEstudanteDisciplina(Estudante estudante, Disciplina disciplina){
        String sql = "INSERT INTO aluno_disciplina (aluno_matr,disciplina_cod,periodo,nota,frequencia) VALUES (?,?,?,?,?)";
        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, estudante.getMatricula());
            pstm.setInt(2, disciplina.getCodigo());
            pstm.setString(3, disciplina.getPeriodo());
            pstm.setFloat(4, estudante.getNota());
            pstm.setFloat(5, estudante.getFrequencia());

            pstm.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public void exibirHistorico(Estudante estudante){
        String sql = "SELECT d.nome,a.disciplina_cod,a.periodo,a.nota,a.frequencia FROM aluno_disciplina a JOIN disciplina d ON a.disciplina_cod = d.codigo WHERE aluno_matr =?";
        ResultSet rs=null;
        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, estudante.getMatricula());
            rs = pstm.executeQuery();
            System.out.println("\n");
            while (rs.next()) {
                System.out.println("Disciplina:" +rs.getString("d.nome"));
                System.out.println("Periodo:" +rs.getString("a.periodo"));
                System.out.println("Nota:" +rs.getString("a.nota"));
                System.out.println("Frequencia:" +rs.getString("a.frequencia")+"%");
                System.out.println("\n");

            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public void exibirTurma(Disciplina disciplina){
        String sql = "SELECT ad.aluno_matr,a.nome,ad.nota,ad.frequencia FROM aluno_disciplina ad JOIN aluno a ON ad.aluno_matr=matricula  WHERE disciplina_cod=? AND periodo=? ";
        ResultSet rs=null;
        try {
            conexao = Conexao.conector();
            pstm = conexao.prepareStatement(sql);
            pstm.setInt(1, disciplina.getCodigo());
            pstm.setString(2, disciplina.getPeriodo());
            rs = pstm.executeQuery();
            System.out.println("\n");
            while (rs.next()) {
                System.out.println("Matricula:" +rs.getInt("ad.aluno_matr"));
                System.out.println("Nome:" +rs.getString("nome"));
                System.out.println("Nota:" +rs.getFloat("ad.nota"));
                System.out.println("Frequencia:" +rs.getFloat("ad.frequencia")+"%");
                System.out.println("\n");

            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conexao != null) {
                    conexao.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
