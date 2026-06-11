package br.com.news.configuration;

import br.com.news.entity.Author;
import br.com.news.entity.News;
import br.com.news.repository.AuthorRepository;
import br.com.news.repository.NewsRepository;
import br.com.news.util.NewsStatus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@Profile("mock")
public class DatabaseSeeder {
	@Bean
	public CommandLineRunner initDatabase(AuthorRepository authorRepository, PasswordEncoder passwordEncoder,
			NewsRepository newsRepository) {

		return args -> {

			System.out.println("================================================================================");
			System.out.println("=== \t\t\t\t\t Profile Mock ativado detectado \t\t\t\t\t ===");
			System.out.println("=== \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ===");
			System.out.println("===   Desative no \"application.properties\" para evitar a inserção de dados   ===");
			System.out.println("================================================================================");
			System.out.println("=== \t\t\t\t\t Iniciando arquivo DatabaseSeeder \t\t\t\t\t ===");
			System.out.println("=== \t\t\t Criando Autores e Notícias caso não existam \t\t\t\t ===");
			System.out.println("=== \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ===");

			seedRedator(authorRepository, passwordEncoder);
			seedRevisor(authorRepository, passwordEncoder);
			seedAuthors(authorRepository, passwordEncoder);
			System.out.println("=== \t\t\t\t\t\t Autores inicializados  \t\t\t\t\t\t ===");

			seedNews(newsRepository, authorRepository);
			System.out.println("=== \t\t\t\t\t\t Notícias inicializadas \t\t\t\t\t\t ===");

			System.out.println("=== \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ===");
			System.out.println("=== \t\t\t\t\t Finalizando arquivo DatabaseSeeder \t\t\t\t ===");
			System.out.println("================================================================================");
		};
	}

	private void seedRedator(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {

		String redatorEmail = "redator@admin.com";

		if (!authorRepository.existsByEmail(redatorEmail)) {
			Author redator = new Author();
			redator.setName("Redator Admin");
			redator.setEmail(redatorEmail);
			redator.setPassword(passwordEncoder.encode("12345678"));
			redator.setBirthDate(LocalDate.now());
			redator.setEducation("Sistemas de Informação");
			redator.setSignature("Redator Admin");
			redator.setEditor(false);

			authorRepository.save(redator);
		}
	}

	private void seedRevisor(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {

		String revisorEmail = "revisor@admin.com";

		if (!authorRepository.existsByEmail(revisorEmail)) {
			Author revisor = new Author();
			revisor.setName("Revisor Admin");
			revisor.setEmail(revisorEmail);
			revisor.setPassword(passwordEncoder.encode("12345678"));
			revisor.setBirthDate(LocalDate.now());
			revisor.setEducation("Sistemas de Informação");
			revisor.setSignature("Revisor Admin");
			revisor.setEditor(true);

			authorRepository.save(revisor);
		}
	}

	private void seedAuthors(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {

		String[] authorNames = {"Gabriel Nathan", "Itallo", "Leonardo", "Nathan"};

		String[] authorEmails = {"gabrielnathan@gmail.com", "itallo@gmail.com", "leonardo@gmail.com", "nathan@gmail.com"};

		String[] authorSignature = {"Gabriel Nathan", "Itallo", "Leonardo", "Nathan"};

		String educations = "Sistemas de Informação";

		for (int i = 0; i < authorNames.length; i++) {

			if (!authorRepository.existsByEmail(authorEmails[i])) {

				Author author = new Author();
				author.setName(authorNames[i]);
				author.setEmail(authorEmails[i]);
				author.setPassword(passwordEncoder.encode("12345678"));
				author.setBirthDate(LocalDate.of(1990 + i, 5, 10));
				author.setEducation(educations);
				author.setSignature(authorSignature[i]);
				author.setEditor(true);

				authorRepository.save(author);

			}
		}
	}

	private void seedNews(NewsRepository newsRepository, AuthorRepository authorRepository) {

		String[] title = {
				"Transformação digital acelera mudanças no mercado brasileiro",
				"Profissionais de tecnologia estão entre os mais disputados de 2026",
				"Universidades investem pesado em inovação e pesquisa aplicada",
				"Pequenas empresas adotam inteligência artificial no dia a dia",
				"Debate sobre sustentabilidade ganha força no setor corporativo",
				"Energia renovável se torna prioridade global em investimentos",
				"Cidades inteligentes começam a mudar a mobilidade urbana",
				"Educação digital redefine o futuro do ensino no Brasil",
				"Startups brasileiras ganham destaque no cenário internacional",
				"Cibersegurança vira prioridade absoluta nas empresas modernas"
		};

		String[] resume = {
				"Empresas aceleram digitalização para se manter competitivas no mercado.",
				"Alta demanda por desenvolvedores e engenheiros de software em 2026.",
				"Instituições fortalecem parcerias com empresas para inovação.",
				"Ferramentas de IA estão sendo adotadas por negócios de pequeno porte.",
				"Empresas reforçam práticas ambientais e sociais em suas estratégias.",
				"Investimentos em energia limpa crescem em ritmo acelerado.",
				"Tecnologias urbanas começam a impactar o cotidiano das cidades.",
				"Ensino remoto e híbrido se consolidam como padrão educacional.",
				"Ecossistema de startups brasileiras atrai investidores estrangeiros.",
				"Aumento de ataques digitais força empresas a reforçarem segurança."
		};

		String[] content = {
				"""
						A transformação digital deixou de ser uma tendência para se tornar uma necessidade estratégica em empresas de todos os portes no Brasil. Organizações estão adotando soluções tecnológicas para automatizar processos, melhorar a experiência do cliente e reduzir custos operacionais.

						O uso de inteligência artificial, computação em nuvem e análise de dados avançada tem permitido decisões mais rápidas e precisas. Isso tem impactado diretamente a competitividade das empresas no mercado global.

						Além disso, a mudança cultural dentro das organizações tem sido essencial para o sucesso dessa transformação. Equipes estão sendo treinadas para lidar com novas ferramentas e modelos de trabalho mais flexíveis.

						A expectativa é que, nos próximos anos, empresas que não se adaptarem à digitalização fiquem em desvantagem significativa no mercado.
						""",
				"""
						O setor de tecnologia continua sendo um dos mais aquecidos do mercado de trabalho brasileiro. Empresas de software, fintechs e startups seguem expandindo suas equipes em busca de profissionais qualificados.

						Áreas como desenvolvimento backend, engenharia de dados, DevOps e segurança da informação estão entre as mais procuradas. A escassez de talentos faz com que salários e benefícios sejam altamente competitivos.

						Profissionais que investem em atualização constante, cursos e certificações têm maiores chances de crescimento rápido na carreira.

						A tendência é que essa demanda continue crescendo com a expansão da transformação digital em todos os setores da economia.
						""",
				"""
						Universidades brasileiras estão intensificando investimentos em pesquisa e inovação tecnológica. Laboratórios modernos e parcerias com empresas privadas estão permitindo avanços significativos em diversas áreas do conhecimento.

						Projetos voltados à inteligência artificial, biotecnologia e engenharia têm ganhado destaque, aproximando a academia do mercado.

						Essa integração entre ensino e prática contribui para a formação de profissionais mais preparados e alinhados às necessidades reais da sociedade.

						O impacto dessas iniciativas deve ser sentido nos próximos anos, com aumento da produção científica e inovação aplicada.
						""",
				"""
						A inteligência artificial deixou de ser exclusividade de grandes corporações e passou a ser acessível para pequenos negócios. Ferramentas baseadas em IA estão sendo usadas para atendimento ao cliente, automação de tarefas e análise de dados.

						Isso permite que pequenas empresas aumentem sua eficiência e reduzam custos operacionais, tornando-se mais competitivas.

						Além disso, soluções de IA generativa estão ajudando na criação de conteúdo, marketing e gestão de processos internos.

						Essa democratização da tecnologia representa uma grande mudança no cenário empresarial global.
						""",
				"""
						A sustentabilidade se tornou um dos principais pilares estratégicos das empresas modernas. Organizações estão adotando práticas mais responsáveis, reduzindo emissões e investindo em projetos sociais.

						O mercado consumidor também está mais exigente, valorizando empresas que demonstram compromisso ambiental.

						Isso tem levado companhias a repensarem suas cadeias de produção e logística para torná-las mais eficientes e sustentáveis.

						A tendência é que práticas ESG se tornem obrigatórias para competitividade no futuro.
						""",
				"""
						O setor de energia renovável vive um momento de expansão global. Investimentos em energia solar e eólica estão crescendo rapidamente, impulsionados pela busca por alternativas sustentáveis.

						Países e empresas estão reduzindo sua dependência de combustíveis fósseis e adotando fontes limpas de energia.

						Isso também tem gerado novos empregos e oportunidades em diferentes regiões do mundo.

						A transição energética é vista como uma das principais transformações econômicas do século XXI.
						""",
				"""
						Cidades inteligentes estão se tornando realidade em diversas regiões do Brasil e do mundo. Tecnologias como sensores urbanos, inteligência artificial e big data estão sendo usadas para melhorar a gestão pública.

						Esses sistemas ajudam a otimizar trânsito, segurança e consumo de energia, tornando as cidades mais eficientes.

						A integração entre tecnologia e planejamento urbano melhora diretamente a qualidade de vida da população.

						O avanço dessas iniciativas deve acelerar nos próximos anos com novos investimentos públicos e privados.
						""",
				"""
						A educação digital transformou completamente a forma como estudantes aprendem e professores ensinam. Plataformas online oferecem conteúdos personalizados e acesso remoto ao conhecimento.

						O modelo híbrido de ensino se consolidou como uma alternativa eficiente e flexível.

						Isso também permitiu maior inclusão educacional, alcançando estudantes de diferentes regiões.

						O futuro da educação está diretamente ligado ao uso inteligente da tecnologia.
						""",
				"""
						O ecossistema de startups no Brasil está em forte expansão. Empresas inovadoras estão atraindo investimentos internacionais e ganhando destaque em setores como fintech, saúde e logística.

						A capacidade de resolver problemas reais com soluções tecnológicas escaláveis é um dos principais fatores desse crescimento.

						Além disso, o ambiente de inovação tem sido impulsionado por aceleradoras e fundos de investimento.

						O Brasil se consolida como um dos polos emergentes de inovação global.
						""",
				"""
						A cibersegurança se tornou uma prioridade essencial para empresas de todos os portes. O aumento de ataques digitais exige investimentos constantes em proteção de dados e infraestrutura.

						Técnicas como autenticação multifator, criptografia avançada e monitoramento em tempo real estão sendo amplamente adotadas.

						Além disso, a conscientização dos colaboradores é vista como um dos pilares mais importantes da segurança digital.

						O futuro do setor depende de inovação contínua para enfrentar ameaças cada vez mais sofisticadas.
						"""
		};

		String[] status = {
				"APROVADA", "APROVADA", "ARQUIVADA", "ESCRITA", "REJEITADA",
				"APROVADA", "APROVADA", "ESCRITA", "ARQUIVADA", "APROVADA"
		};

		String[] publicated_at = {
				"2026-01-05T09:00:00",
				"2026-01-06T10:00:00",
				null,
				null,
				null,
				"2026-01-10T14:10:00",
				"2026-01-11T15:45:00",
				null,
				null,
				"2026-01-14T18:00:00"
		};

		String[] authorEmails = {
				"gabrielnathan@gmail.com",
				"itallo@gmail.com",
				"leonardo@gmail.com",
				"nathan@gmail.com",
				"gabrielnathan@gmail.com",
				"itallo@gmail.com",
				"leonardo@gmail.com",
				"nathan@gmail.com",
				"gabrielnathan@gmail.com",
				"itallo@gmail.com"
		};

		for (int i = 0; i < title.length; i++) {

			if (!newsRepository.existsByTitle(title[i])) {

				News news = new News();
				news.setTitle(title[i]);
				news.setResume(resume[i]);
				news.setContent(content[i]);
				news.setStatus(NewsStatus.valueOf(status[i]));

				if (publicated_at[i] != null) {
					news.setPublicatedAt(LocalDateTime.parse(publicated_at[i]));
				}

				String email = authorEmails[i];
				Author author = authorRepository.findByEmail(email)
						.orElseThrow(() -> new RuntimeException("Autor não encontrado: " + email));

				news.setAuthor(author);
				newsRepository.save(news);
			}
		}
	}
}