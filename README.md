<h1>JuMarket-API</h1>
<p align="center">API Rest para JuMarket</p>
<p align="center">
     <a>
        <img alt="Java" src="https://img.shields.io/badge/Java-v17-blue.svg" />
    </a>
    <a>
        <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-v1.8.22-purple.svg" />
    </a>
    <a>
        <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-v3.1.1-brightgreen.svg" />
    </a>
    <a>
        <img alt="Gradle" src="https://img.shields.io/badge/Gradle-v8.1.1-lightgreen.svg" />
    </a>
    <a>
        <img alt="H2" src="https://img.shields.io/badge/H2-v2.1.210-darkblue.svg" />
    </a>
    <a>
        <img alt="Flyway" src="https://img.shields.io/badge/Flyway-v9.20-red.svg">
    </a>
</p>

<h3>Problema proposto</h2>
<p>Uma mercearia do Bairro Bom Descanso chamada JuMarket necessita de uma solução para venda de autoatendimento, para tanto necessitamos desenvolver as seguintes funcionalidades:</p>

<h4>Cadastro de categorias:</h4>
<p>Um cadastro de categorias de produto, apenas contendo a nome da categoria, por exemplo: Produtos de Limpeza, Bebidas, Bombonier, Salgadinhos, Chás e Cafes, Grãos e Cereais e outros;</p>

<h4>Cadastro de produtos:</h4>
<p>Um cadastro contendo os produtos do mercado, este cadastro poderá conter os seguintes campos:<br>
Nome do produto, unidade de medida, preço unitário;</p>

<h4>Carrinho</h4>
<p>O carrinho é a funcionalidade na qual o usuário selecionou os produtos que deseja adquirir, neste caso conter os seguintes dados: produto, quantidades de itens e o preço de venda;</p>

<h4>Finalização da venda:</h4>
<p>Ao finalizar a venda deverá ser informado o valor total e a forma de pagamento escolhida, as opções são: Cartão de crédito/ débito / dinheiro e pix;</p>

<h4>Restrições:</h4>
<p>
A implementação deve utilizar linguagem backend: Kotlin com o Springboot, o Banco de dados utilizado tem que subir via docker, utilizar um gerenciador de migration (Flyway, Liquibase ou outro);<br> JuMarket Use todos os seus conhecimentos adquiridos no bootcamp para explorar bem a solução. Não se preocupe, porque não existe certo ou errado. Só queremos conhecer um pouco mais sobre você. Caso queira implementar alguma funcionalidade não descrita sinta-se à vontade tudo será considerado na análise do seu código, inclusive não se esqueça dos testes, nossa sugestão é utilizar o Junit para testar pelo menos a camada de serviço;<br> Utilize o GitHub para repositório de código.</p>

<h3>Descrição da Solução</h3>

<h4>Alterações do problema proposto</h4>

<p>Como solução decidi incrementar a solução adicionando uma opção do cliente adicionar um cupom para a compra que pode dar um desconto fixo de X ou um de desconto percentual de X%.</p>

<p>Para as propriedades de produto foi adicionando o produto pertencer há uma categoria e mudando a propriedade de preço para o produto.</p>

<p>No carrinho foi retirado o preço de venda e passado para o produto.</p>

<p>E para finalizar a venda foi adicionado a possibilidade de adicionar um cupom de desconto, o valor de venda com desconto, valor total da venda, data da venda.</p>

<h4>API Publica de JuMarket</h4>

<p>Para a parte pública da API tem as seguintes opções:</p>

* Buscar produto por nome
* Listar produtos disponíveis
* Listar produtos por Categoria
* Listar Categorias
* Criar carrinho com produtos
* Adicionar produto no carrinho
* Alterar quantidade de produtos de um carrinho
* Remover Produto do carrinho
* Listar Informações do carrinho
* Finalizar compra do carrinho

<h4>API Privada de JuMarket</h4>

<p>Para a parte privada da API, somente usuários autenticados tem os seguintes opções:</p>

* Cadastrar categoria
* Cadastrar produto
* Cadastrar cupom
* Atualizar produto
* Informações do produto
* Listar cupons
* Listar vendas de um dia

<h4>Validações que a API</h4>

* Disponibilidade do produto para adição ao carrinho
* Se o cupom é valido para a aplicação do desconto

<h4 align="center">Diagrama ER do banco de dados do JuMarket</h4
<figure>
<p align="center">
  <img src="assets/JuMarket.png"  alt="API para Sistema de Avaliação de Créditos"/><br>
</p>
</figure>

<h3>Possíveis melhorias para a solução proposta:</h3>

<p>Nesse tópico está as possíveis melhorias que podem ser efetuada na estrutura, mas pelo tempo do desafio não será implementado</p>

* Colocar uma data de expiração para o carrinho
* Desativar a venda de um produto
* Criar um cadastro de usuário para realizar uma compra
* Cupons ter um limite de uso
* Listar compras realizadas em uma faixa de tempo
* Adicionar quantidade disponível para venda de um produto
* Paginações para as listagens de produto, categoria, carrinhos, vendas, cupons
* Melhora dos tratamento dos nullables
* Melhorar no retorno de erro da api, principalmente nos erros com relação ao banco de dados

<h3>Como executar o projeto:</h3>