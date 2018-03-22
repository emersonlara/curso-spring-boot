# Curso Spring Boot


<!-- TOC -->

- [Curso Spring Boot](#curso-spring-boot)
- [Antes de começar](#antes-de-começar)
- [Preparando o ambiente](#preparando-o-ambiente)
- [Banco de dados](#banco-de-dados)
- [Criando o projeto](#criando-o-projeto)
    - [Usando o STS](#usando-o-sts)
    - [Usando o Spring Initializr](#usando-o-spring-initializr)
- [Mapeamento](#mapeamento)
    - [Anotando a entidade](#anotando-a-entidade)
    - [Definindo o nome da tabela](#definindo-o-nome-da-tabela)
    - [Definindo a chave primária](#definindo-a-chave-primária)
    - [Definindo os outros campos](#definindo-os-outros-campos)
    - [Definindo campos nulos](#definindo-campos-nulos)
- [Configurando o acesso ao banco de dados](#configurando-o-acesso-ao-banco-de-dados)
- [Repository (acesso a dados)](#repository-acesso-a-dados)

<!-- /TOC -->


Este Curso visa introduzir o desenvolvimento de aplicações utilizando o Spring Boot.

# Antes de começar

O Spring Boot possui uma série de funcionalidades distintas que podem ser adicionadas a medida que o projeto ganha forma. O foco neste momento é exibir como criar uma aplicação RESTfull, onde chamamos notoriamente de **backend**. A outra parte, chamada de **frontend**, será vista em outro Curso. Em suma, o **backend** é responsável em fornecer um serviço web que pode ser chamado através de uma URL e responder o que foi requisitado em JSON.

[Subir](#curso-spring-boot)

# Preparando o ambiente

O Spring Boot necessita dos seguintes itens:

- O [JDK](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html), nesse momento na versão 8, é o kit de desenvolvimento para aplicações em Java. Após a instalação, é útil checar se existe as variáveis de ambiente JAVA_HOME e JRE_HOME, conforme a figura a seguir.

<p align="center">
<img src="https://user-images.githubusercontent.com/1509692/37594988-cfd21506-2b55-11e8-82a1-0f7cc7610073.png">
</p>

- O [Maven](https://maven.apache.org/download.cgi) que é o gerenciador de pacotes do Java. O Maven se resume a ler o arquivo "pom.xml" e sincronizar as dependências que o seu projeto usa. Por exemplo, se necessitas usar o adaptador Oracle para conexão com o banco de dados, será no *pom.xml* que adicionaremos essa dependência e a IDE irá baixar todos os pacotes automaticamente. Para instalar o Maven, basta fazer o download, descompactar em algum lugar do sistema e adicionar o seu caminho nas variáveis de ambiente PATH, conforme o exemplo a seguir:

<p align="center">
<img src="https://user-images.githubusercontent.com/1509692/37595157-7cd29d66-2b56-11e8-8c46-18c325fdd624.png">
</p>

- A IDE que possui diversas facilidades para o desenvolvimento de aplicações Spring Boot. Usaremos o STS (*Spring Tool Suite*), que é um Eclipse modificado para o Spring Boot. O download dele está [aqui](https://spring.io/tools/sts). Basta fazer o download e executar o ".exe"

Para o desenvolvimento apenas estes três itens são necessários. Com tudo isso instalado, podemos criar a nossa primeira aplicação.

[Subir](#curso-spring-boot)

# Banco de dados

Neste Curso estaremos utilizando o MySql. Pode-se instalar apenas o MySql Server no sistema, sendo acessível através da linha de comando (shell). Para instalar o MySql, acesse [este link](https://dev.mysql.com/downloads/installer/).

Com o MySql instalado, acesse-o através do comando `mysql -u root -p`. Será solicitada a senha que foi cadastrada na instalação, e após digitada a senha, você terá acesso ao console do mysql.

Vamos fazer duas operações simples. A primeira, é criar uma database chamada `springboot`, através do comando `create database`:

<p align="center">
<img src="https://i.imgur.com/ZAIv2ra.png">
</p>

Após criar o database, use o comando `use springboot;` para selecioná-lo. A partir daí pode-se criar uma tabela.

A segunda tarefa é criar a tabela `Users`, da seguinte forma:

```sql
CREATE TABLE Users (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
firstname VARCHAR(30) NOT NULL,
lastname VARCHAR(30) NOT NULL,
email VARCHAR(50),
reg_date TIMESTAMP
);
```

<p align="center">
<img src="https://i.imgur.com/jIa5CBV.png">
</p>

Pode-se verificar a estrutura de uma tabela com o comando `desc Users;`

<p align="center">
<img src="https://i.imgur.com/fFuJO79.png">
</p>


# Criando o projeto

Existem duas formas corretas de se criar um projeto Spring Boot. Você pode usar o Wizard do STS, ou então acessando o site [Spring Initializr](https://start.spring.io/).

[Subir](#curso-spring-boot)

## Usando o STS

Com o STS aberto, navegue até `File > New > Spring Starter Project`. Surge um Wizard, com a seguinte tela inicial:

<p align="center">
<img src="https://i.imgur.com/9PRdwhv.png">
</p>

Temos que preencher alguns campos nesta tela, dentre eles:

- **Name** É o nome do projeto, que também será usado para criar o diretório do mesmo. Você pode adicionar, por exemplo, `meuProjeto`.
- **Type** Deixe `maven` selecionado
- **Packaging** A forma como o projeto será "empacotado". Deixe o valor padrão, `jar`. Isso significa que o projeto todo será compilado para um único arquivo `.jar`, que poderá ser executado pelo Maven. Existe também a opção `.war` que é uma outra forma de empacotar o projeto, só que para ser usada como um container de algum servidor web, como o Tomcat. 
- **Java version** A versão do Java. Usamos a 8 como padrão, mas se houver uma superior não é necessário rebaixá-la.
- **Language** Existem hoje, além do Java, as linguagem Kotlin e Grovvy. Todas as 3 são compiladas no mesmo ByteCode, então você pode escolher a linguagem que estiver mais acostumado. Nesse Curso estaremos utilizando sempre Java.
- **Group** O group é o início da definição do seu package. Um "package" deve ser um namespace "único" do seu projeto, em relação a qualquer projeto Java. Para se obter isso, é aconselhável usar a url do seu site pessoal ou da empresa onde trabalha, de forma invertida. Por exemplo, se o meu site pessoal é `danielschmitz.com.br`, o Group seria `br.com.danielschmitz`. Se a empresa é `www.ufjf.br`, o Group é `br.ufjf`
- **Artifact** é o nome do projeto que será concatenado ao *Group*. Suponha que o seu projeto se chama "Gestão de Médicos ProDoctor", um bom nome de Artifact seria somente `prodoctor`. 
- **Version** É a versão do seu software. Deixe com -SNAPSHOT no final para que a versão seja compilada no modo desenvolvimento, com ferramentas extras de Debug, por exemplo.
- **Description** A descrição do projeto
- **package** O caminho completo do pacote da sua aplicação. É comum usar o *Group* junto com o *Artifact*. No caso deste Curso, estamos usando `br.com.danielschmitz.meuprojeto`, então lembre-se de alterar isso de acordo com a sua necessidade. 

Após estas configurações, poderíamos ter a seguinte estrutura:

<p align="center">
<img src="https://i.imgur.com/Rv2csGB.png">
</p>


Clique no botão `Next` para seguir para a próxima tela do Wizard.

Nesta tela nós escolhemos as dependências do projeto, isso é, os módulos que compõem o projeto. Para este Curso, vamos escolher os seguintes módulos:

- **DevTools** Fornece uma série de facilidades, dentre elas a habilidade de reiniciar o servidor instantaneamente após uma modificação no código fonte, e também com live-reload, que atualiza a página web após uma modificação.
- **JPA** É a camada de persistência do Spring/Java, incluindo o Hibernate por exemplo. Através do JPA vamos criar classes java que são o mapeamento de uma Tabela e realizar operações de inclusão, exclusão e alteração de dados.
- **Web** Provê todas as funcionalidades Web junto com o servidor TomCat, permitindo que se possa criar rotas RESTfull de forma fácil através de *anotations*.
- **MySql** O módulo MySql para que o JPA conecte no banco. 

Após selecionar estes módulos, temos algo semelhante a:

<p align="center">
<img src="https://i.imgur.com/Cx85D1L.png">
</p>

Clique no botão `Finish` para criar o projeto. Após a criação do projeto, o Maven entra em ação e lê o arquivo `pom.xml` baixando todas as dependências. Isso pode demorar um pouco...

<p align="center">
<img src="https://i.imgur.com/tUPZDyc.png">
</p>

[Subir](#curso-spring-boot)

## Usando o Spring Initializr

Acesse o site [Spring Initializr](https://start.spring.io/) para ter acesso a um formulário semelhante a figura a seguir:

<p align="center">
<img src="https://i.imgur.com/aHvibnS.png">
</p>

Preencha as seguintes informações:

- **Group** O group é o início da definição do seu package. Um "package" deve ser um namespace "único" do seu projeto, em relação a qualquer projeto Java. Para se obter isso, é aconselhável usar a url do seu site pessoal ou da empresa onde trabalha, de forma invertida. Por exemplo, se o meu site pessoal é `danielschmitz.com.br`, o Group seria `br.com.danielschmitz`. Se a empresa é `www.ufjf.br`, o Group é `br.ufjf`
- **Artifact** é o nome do projeto que será concatenado ao *Group*. Suponha que o seu projeto se chama "Gestão de Médicos ProDoctor", um bom nome de Artifact seria somente `prodoctor`. 
- **Dependencies** Usado para adicionar as dependências do projeto. Você pode digitar a dependência e selecionar de uma lista. As dependências para esse Curso são:
    - **DevTools** Fornece uma série de facilidades, dentre elas a habilidade de reiniciar o servidor instantaneamente após uma modificação no código fonte, e também com live-reload, que atualiza a página web após uma modificação.
    - **JPA** É a camada de persistência do Spring/Java, incluindo o Hibernate por exemplo. Através do JPA vamos criar classes java que são o mapeamento de uma Tabela e realizar operações de inclusão, exclusão e alteração de dados.
    - **Web** Provê todas as funcionalidades Web junto com o servidor TomCat, permitindo que se possa criar rotas RESTfull de forma fácil através de *anotations*.
    - **MySql** O módulo MySql para que o JPA conecte no banco. 

Após adicionar as dependências, clique no botão `Generate Project` para realizar o download de um arquivo `.zip`.

<p align="center">
<img src="https://i.imgur.com/uXtBEqo.png">
</p>

Descompacte o arquivo `.zip` e use a opção `File > Open Project From File System` do STS. 

[Subir](#curso-spring-boot)

# Mapeamento

Uma das funcionalidades básicas do Java, em conjunto com o JPA e o Hibernate, é mapear uma tabela do banco de dados, gerando uma classe Java que representa a tabela. 

Vamos criar manualmente o mapeamento da classe Users, que dará acesso a tabela Users. 

Clique com o botão direto do mouse no projeto "meuprojeto" e escolha a opção `New > Class`. Surge o Wizard para a criação da classe. Em Package, complemente com `.model.map` em relação ao seu package, e em Name, coloque `User`. Clique no botão `Finish`.

<p align="center">
<img src="https://i.imgur.com/LK5PE7C.png">
</p>

Após a criação da classe, temos algo como:

```java
package br.com.danielschmitz.meuprojeto.model.map;

public class User {

}
```

[Subir](#curso-spring-boot)

## Anotando a entidade

Agora começamos a fazer algo chamado de "anotar a classe". No Java, `annotations` são usados frequentemente para configurar algo. É melhor usar uma anotação do que um arquivo xml. Nossa primeira anotação é dizer que essa classe é uma entidade JPA, então temos:

```java
package br.com.danielschmitz.meuprojeto.model.map;

@Entity
public class User {

}
```

Após adicionar o `@Entity`, surgirá um erro dizendo que a anotação não foi encontrada. Para resolver esse tipo de erro, aperte `ctrl+shift+o` e selecionando o package `javax.persistence.Entity` (caso haja 2 classes com o mesmo nome e packages diferentes).

```java
package br.com.danielschmitz.meuprojeto.model.map;

import javax.persistence.Entity;

@Entity
public class User {
	
}
```

[Subir](#curso-spring-boot)

## Definindo o nome da tabela

Perceba que o nome da classe é diferente do nome da tabela, então devemos adicionar uma anotação para dizer qual é o nome da tabela. Neste caso usamos o `@Table`, veja:

```java
package br.com.danielschmitz.meuprojeto.model.map;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class User {

}
```

[Subir](#curso-spring-boot)

## Definindo a chave primária

A chave primária é definida pela anotação `@Id`, e também definimos uma estratégia para auto incremento através da anotação `@GeneratedValue`, veja:

```java
package br.com.danielschmitz.meuprojeto.model.map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
}
```

[Subir](#curso-spring-boot)

## Definindo os outros campos

Os campos da tabela pode ser referenciados através de propriedades da classe, veja:

```java
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String firstname;
	private String lastname;
	private String email;
	private Date reg_date;
	
}
```

O que podemos perceber neste momento é que todas as propriedades são do tipo `private`, ou seja, elas são visíveis apenas na classe. Para que possamos deixá-las expostas, usamos a refatoração do STS para gerar gets/sets. Clique com o botão direito do mouse e acesse `Source > Generate Getters and Setters`.

[Subir](#curso-spring-boot)

## Definindo campos nulos

A tabela Users possui alguns campos que não podem ser nulos. Para definir este comportamento no mapeamento, usamos a anotação `@NotBlank`.

```java
package br.com.danielschmitz.meuprojeto.model.map;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	private String firstname;
	
	@NotBlank
	private String lastname;
	
	private String email;
	
	@NotBlank
	private Date reg_date;

    //get & set...

}
```

[Subir](#curso-spring-boot)

# Configurando o acesso ao banco de dados 

Antes de prosseguirmos temos que dizer as configurações de acesso do MySql para o nosso projeto. Isso é feito no arquivo `src/main/resources/application.resources`, incluindo as seguintes entradas:

```txt
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.username=root
spring.datasource.password=123456
```

A primeira configuração, `spring.jpa.hibernate.ddl-auto` configura qual o comportamento do JPA em relação ao banco de dados. Temos as seguintes opções:

- `none` Não há alterações na estrutura das tabelas do banco de dados
- `update` Há alterações nas tabelas de acordo com as alterações nas entidades
- `create` Cria as tabelas toda vez que a instância é criada, mas nao a destroi quando a instância é fechada.
- `create-drop` Cria as tabelas toda vez que a instância é criada e a destroi (drop) quando a instância é fechada.

Através desta configuração podemos criar toda a estrutura de banco de dados partindo do mapeamento JPA das entidades. Pode, a princípio, usar `create` na primeira vez que executarmos a aplicação, e depois alterar para `update` nas próximas execuções.

[Subir](#curso-spring-boot)

# Repository (acesso a dados)

No JPA temos o conceito de Repository, que é um "repositório" de ações na classe `@Entity` que podem ser executadas de forma automática. Para a classe `User`, podemos criar uma interface `UserRepository` que irá extender da interface `JPARepository`. Acesse `File > New > Interface` e inclua as seguintes informações:

<p align="center">
<img src="https://i.imgur.com/30pcm8e.png">
</p>

Após criada a classe, devemos alterar o tipo genérico <T> da interface, e fornecer o tipo de variável que corresponde a chave da classe `User`. teoricamente alteramos `extends JpaRepository<T, ID>` para `extends JpaRepository<User, Long>`, deixando a interface da seguinte forma:

```java
package br.com.danielschmitz.meuprojeto.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.danielschmitz.meuprojeto.model.map.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
```

Esta interface pode estar recheada de métodos que tem como objetivo realizar operações na tabela. O exemplo a seguir ilustra como obter um registro na tabela dado o campo id, veja:

```java
public interface UserRepository extends JpaRepository<User, Long> {

	public User findOne(Long id);
	
}
```

Agora vamos criar um método que retorna todos os Usuários, da seguinte forma:

```java
public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findOne(Long id);
	
	public List<User> findAll();
	
}
```

E que tal criar um método para salvar o usuário? Temos:

```java
public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findOne(Long id);
	
	public List<User> findAll();
	
	public User save(User user);
	
}
```

É claro que temos operações mais complexas do que estas, mas por enquanto vamos focar no mais simples.





 
