# Task Tracker CLI

Um aplicativo de linha de comando (CLI) simples e eficiente para gerenciar suas tarefas e lista de afazeres.

## 📝 Descrição

O Task Tracker é uma ferramenta CLI que permite rastrear e gerenciar suas tarefas de forma organizada. Este projeto demonstra conceitos fundamentais de programação, incluindo manipulação de sistema de arquivos, tratamento de entrada do usuário e construção de aplicações CLI robustas.

## ✨ Funcionalidades

- ✅ Adicionar novas tarefas
- 📝 Atualizar descrições de tarefas existentes
- 🗑️ Remover tarefas
- 🔄 Alterar status das tarefas (todo, in-progress, done)
- 📋 Listar todas as tarefas
- 🔍 Filtrar tarefas por status
- 💾 Persistência de dados em arquivo JSON

## 🚀 Como Usar

### Instalação

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/task-tracker-cli.git
cd task-tracker-cli

# Compile e instale (exemplo para Java)
mvn clean install
```

### Comandos Disponíveis

#### Adicionar uma nova tarefa
```bash
task-cli add "Comprar mantimentos"
# Output: Task added successfully (ID: 1)
```

#### Atualizar uma tarefa existente
```bash
task-cli update 1 "Comprar mantimentos e preparar jantar"
```

#### Remover uma tarefa
```bash
task-cli delete 1
```

#### Marcar tarefa como em progresso
```bash
task-cli mark-in-progress 1
```

#### Marcar tarefa como concluída
```bash
task-cli mark-done 1
```

#### Listar todas as tarefas
```bash
task-cli list
```

#### Listar tarefas por status
```bash
task-cli list done
task-cli list todo
task-cli list in-progress
```

## 📋 Estrutura de Dados

Cada tarefa possui as seguintes propriedades:

| Propriedade | Tipo | Descrição |
|-------------|------|-----------|
| `id` | Integer | Identificador único da tarefa |
| `description` | String | Descrição breve da tarefa |
| `status` | Enum | Status atual (`todo`, `in-progress`, `done`) |
| `createdAt` | LocalDateTime | Data e hora de criação |
| `updatedAt` | LocalDateTime | Data e hora da última atualização |

### Exemplo de estrutura JSON:
```json
[
    {
      "id": 1,
      "description": "Comprar mantimentos",
      "status": "todo",
      "createdAt": "2024-01-15T10:30:00",
      "updatedAt": "2024-01-15T10:30:00"
    }
]
```



## 🛠️ Tecnologias Utilizadas

- **Java 17+** - Linguagem principal
- **Maven** - Gerenciamento de dependências
- **Manipulação JSON Manual** - Serialização/deserialização customizada
- **JUnit 5** - Framework de testes
- **AssertJ** - Assertions fluentes para testes

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

Desenvolvido como parte dos projetos práticos do [roadmap.sh](https://roadmap.sh/projects/task-tracker).

---

**Nota**: Este projeto é ideal para praticar conceitos de programação orientada a objetos, clean code e arquitetura limpa. A implementação manual do JSON parser oferece uma oportunidade excelente para compreender os fundamentos de serialização/deserialização de dados. Cada implementação pode servir como base para projetos mais complexos e aplicações do mundo real.
