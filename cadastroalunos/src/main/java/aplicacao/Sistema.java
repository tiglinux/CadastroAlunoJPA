package aplicacao;

import java.util.List;

/**
 * @author tiago
 * @version 1.0
 * 
 * Using JPA/JPQL and JAVA for the CRUD Student's system.
 */

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import dominio.Aluno;

public class Sistema {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String nome = null;
		String novoNome = null;
		boolean logical = true;
		int idade = 0;
		int option = 0;
		int idRemover;
		int idUpdate;
		int novaIdade;
		

		while (logical) {
			System.out.println("CRUD JPA Alunos System");
			System.out.println("1 - para Cadastrar Aluno ");
			System.out.println("2 - para Remover Aluno ");
			System.out.println("3 - para Atualizar Aluno ");
			System.out.println("0 - para sair ");

			System.out.println("Digite uma opção: ");
			option = scanner.nextInt();

			if (option == 0) {
				System.out.println("Programa Encerrado!");
				break;
			} else if (option == 1) {
				System.out.println("Digite o nome do Aluno: ");
				nome = scanner.next();

				System.out.println("Digite a idade do Aluno: ");
				idade = scanner.nextInt();

				Aluno a = new Aluno(null, nome, idade);

				EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudAlunoDB");
				EntityManager em = emf.createEntityManager();

				em.getTransaction().begin();
				em.persist(a);
				em.getTransaction().commit();

				System.out.println(a);
				System.out.println("Aluno Cadastrado com sucesso!\n");

				em.close();
				emf.close();

			} else if (option == 2) {
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudAlunoDB");
				EntityManager em = emf.createEntityManager();
				
				String jpql = "select a from Aluno a";
				TypedQuery<Aluno> query = em.createQuery(jpql,Aluno.class);
				
				List<Aluno> alunos = query.getResultList();
				
				System.out.println("-----Registros encontrados-----");
				for (Aluno aluno : alunos) {
					System.out.println("ID: " + aluno.getId() 
						+ " Nome: " + aluno.getName() + " Idade: " + aluno.getAge());
				}
				
				System.out.println();
				
				System.out.println("Digite o ID do aluno que deseja excluir: ");
				idRemover = scanner.nextInt();
				
				em.getTransaction().begin();
				em.remove(em.find(Aluno.class, idRemover));
				em.getTransaction().commit();
				
				em.close();
				emf.close();

			} else if (option == 3) {
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("crudAlunoDB");
				EntityManager em = emf.createEntityManager();
				
				String jpql = "select a from Aluno a";
				TypedQuery<Aluno> query = em.createQuery(jpql,Aluno.class);
				
				List<Aluno> alunos = query.getResultList();
				
				System.out.println("-----Registros encontrados-----");
				for (Aluno aluno : alunos) {
					System.out.println("ID: " + aluno.getId() 
						+ " Nome: " + aluno.getName() + " Idade: " + aluno.getAge());
				}
				
				em.getTransaction().begin();
				
				System.out.println("Digite o ID do aluno que deseja alterar: ");
				idUpdate = scanner.nextInt();
				
				System.out.println("Digite o novo NOME do aluno que deseja alterar: ");
				novoNome = scanner.next();
				
				System.out.println("Digite a nova IDADE do aluno que deseja alterar: ");
				novaIdade = scanner.nextInt();
				
				Aluno aluno = em.find(Aluno.class, idUpdate);
				aluno.setName(novoNome);
				aluno.setAge(novaIdade);
				
				em.merge(aluno);
				em.getTransaction().commit();
				
				em.close();
				emf.close();
				
			}

		}

	}

}
