# 📚 Sistema de Gerenciamento de Biblioteca Escolar

Este projeto é um sistema desktop completo desenvolvido em Java com o objetivo de gerenciar empréstimos de livros em uma biblioteca escolar. Ele permite o cadastro de obras, usuários, controle de empréstimos e geração de relatórios personalizados em PDF.

## 🎯 Objetivo

Aplicar os conceitos de Programação Orientada a Objetos (POO) por meio do desenvolvimento de um sistema realista, com separação de responsabilidades (MVC) e integração com bibliotecas externas.

---

## ⚙️ Funcionalidades

- ✅ Cadastro de obras e usuários
- 📖 Registro de empréstimos e devoluções
- 📅 Verificação de atrasos
- 📊 Geração de relatórios em PDF:
  - Lista completa de obras
  - Lista de usuários
  - Empréstimos realizados em um mês específico
  - Obras mais emprestadas
  - Usuários com mais atrasos

---

## 🖥️ Interface Gráfica

A interface foi desenvolvida com Java Swing, utilizando caixas de seleção, campos de texto e tabelas para facilitar a interação com o usuário. A geração dos relatórios utiliza o `JFileChooser` para permitir ao usuário escolher onde salvar o arquivo PDF.

---

## 📄 Geração de PDF

A biblioteca [OpenPDF](https://github.com/LibrePDF/OpenPDF) foi utilizada para gerar os relatórios. Cada relatório é gerado dinamicamente com base nos dados do sistema, organizados em tabelas com cabeçalhos e estilização adequada.

---

## 📦 Dependências

Gerenciadas via Maven:

```xml
<dependencies>
    <!-- Geração de PDFs -->
    <dependency>
        <groupId>com.github.librepdf</groupId>
        <artifactId>openpdf</artifactId>
        <version>1.3.30</version>
    </dependency>

    <!-- Manipulação de arquivos JSON -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
</dependencies>
