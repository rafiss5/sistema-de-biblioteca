# 📚 Sistema de Biblioteca com Relatórios em PDF

Este é um sistema de gerenciamento de biblioteca desenvolvido em Java. Ele permite a geração de relatórios diversos em **PDF**, utilizando a biblioteca **OpenPDF**, com uma interface gráfica intuitiva feita com **Java Swing**.

## ✨ Funcionalidades

- 📄 Geração de relatórios personalizados:
  - Lista de Obras
  - Lista de Usuários
  - Empréstimos do Mês (com seleção de mês e ano)
  - Obras Mais Emprestadas
  - Usuários com Mais Atrasos
- 📁 Escolha de caminho para salvar o PDF com `JFileChooser`
- 🖥️ Interface gráfica desenvolvida com Java Swing

## 🛠️ Tecnologias Utilizadas

- Java 17
- Swing (Interface gráfica)
- OpenPDF (Geração de PDF)
- Gson (Manipulação de JSON, se necessário)
- Maven (Gerenciador de dependências)

## 📦 Dependências (pom.xml)

```xml
<dependencies>
    <!-- Geração de PDF com OpenPDF -->
    <dependency>
        <groupId>com.github.librepdf</groupId>
        <artifactId>openpdf</artifactId>
        <version>1.3.30</version>
    </dependency>

    <!-- Manipulação de JSON com Gson -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
</dependencies>
