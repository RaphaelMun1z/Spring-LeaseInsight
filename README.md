<h1>MyAdmin</h1>
<p>MyAdmin é um software desenvolvido em Java Spring Boot para administrar a locação de imóveis. Ele oferece uma interface robusta e intuitiva para gerenciar propriedades, inquilinos, contratos de aluguel e pagamentos, facilitando a administração e a organização das informações.</p>

<h2>Arquitetura</h2>
<p>O projeto MyAdmin utiliza a Arquitetura em Camadas (Layered Architecture), promovendo a separação de responsabilidades e a modularidade do código. A estrutura do projeto é organizada em três camadas principais:</p>
<ol>
  <li>
    <p>Camada de Apresentação (Presentation Layer):</p>
    <ul>
      <li>Implementada com Controladores REST que lidam com as requisições HTTP e retornam as respostas adequadas.</li>
    </ul>
  </li>
  <li>
    <p>Camada de Serviço (Service Layer):</p>
    <ul>
      <li>Contém a lógica de negócios da aplicação. Os serviços chamam os repositórios para acessar e manipular os dados conforme necessário.</li>
    </ul>
  </li>
  <li>
    <p>Camada de Persistência (Persistence Layer):</p>
    <ul>
      <li>Gerencia a interação com o banco de dados. Os Repositórios são responsáveis por executar operações de CRUD (Create, Read, Update, Delete) no banco de dados.</li>
    </ul>
  </li>
</ol>

<img align="center" alt="architecture" src="https://miro.medium.com/v2/resize:fit:1400/1*neBcAZJyLGpE7KHc3sH8bw.png" alt="architecture" width="500" height="228"/>

<h2>Tecnologias Utilizadas</h2>
<div>
  <img align="center" alt="Rapha-Java" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-plain.svg">
  <img align="center" alt="Rapha-Spring" src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="30" height="30"/>
</div>
<br>
<ul>
  <li>Java 21</li>
  <li>Spring Boot 3</li>
  <li>JPA/Hibernate</li>
  <li>H2 Database</li>
  <li>Maven</li>
</ul>
