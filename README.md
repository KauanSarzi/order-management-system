## O ciclo de trabalho (faça isso toda vez)

### Passo 1 — Baixe o projeto pela primeira vez

Acesse o repositório no GitHub, copie o link e rode no terminal:

```bash
git clone <URL_do_repositório>
cd nome-do-repositório
```

> *Só precisa fazer isso uma vez. Depois disso, vá direto pro Passo 2.*

---

### Passo 2 — Atualize seu código antes de começar

Sempre que for trabalhar, comece por aqui para garantir que você tem a versão mais recente:

```bash
git checkout main
git pull origin main
```

---

### Passo 3 — Crie sua branch

Pense na branch como o seu "espaço de trabalho pessoal". Você trabalha nela sem afetar o código dos outros.

```bash
git checkout -b nome-da-sua-branch
```

Use nomes que descrevam o que você vai fazer, por exemplo:
- `feat/tela-de-login`
- `fix/erro-no-calculo`
- `chore/atualiza-dependencias`

---

### Passo 4 — Desenvolva

Agora é com você. Abra o projeto no seu editor e faça as alterações da sua tarefa. Tente focar só no que é necessário para a sua tarefa — evite mexer em arquivos que não têm nada a ver com ela.

---

### Passo 5 — Salve suas alterações (commit)

Quando terminar uma parte do trabalho, salve o progresso:

```bash
git add .
git commit -m "descrição do que você fez"
```

Exemplos de boas mensagens de commit:
- `"adiciona validação no formulário de login"`
- `"corrige cálculo do total do carrinho"`
- `"remove console.log esquecido"`

> Dica: faça commits pequenos e frequentes. É muito mais fácil de revisar e de reverter se algo der errado.

---

### Passo 6 — Envie para o GitHub

```bash
git push origin nome-da-sua-branch
```

---

### Passo 7 — Abra um Pull Request (PR)

É aqui que você pede para o seu código entrar no projeto oficial.

1. Acesse o repositório no GitHub
2. Clique em **"New pull request"**
3. Escolha sua branch como origem e a `main` como destino
4. Escreva um título claro e uma descrição do que foi feito
5. Clique em **"Create pull request"**

> Ainda não terminou? Abra como **Draft Pull Request** para avisar a equipe que ainda está em andamento.

---

### Passo 8 — Aguarde a revisão

Os outros membros vão olhar seu código e podem aprovar, comentar ou pedir ajustes. Isso é normal e faz parte do processo. Faça os ajustes pedidos, suba os novos commits na mesma branch e a PR vai atualizar automaticamente.

---

### Passo 9 — Merge e limpeza

Após aprovação, um mantenedor vai integrar seu código na `main`. Depois disso, atualize sua máquina:

```bash
git checkout main
git pull origin main
```

Pronto, pode começar o ciclo de novo para a próxima tarefa.

---

## Resumo visual do ciclo

pull main → cria branch → desenvolve → commit → push → abre PR → revisão → merge → pull main → repete

---

## Regras importantes

- Nunca faça alterações direto na `main`
- Sempre abra uma PR para revisão, mesmo que seja algo pequeno
- Avise a equipe o que você está desenvolvendo para ninguém fazer a mesma coisa
- Resolva conflitos antes de pedir revisão

---

## E se aparecer um conflito?

Conflito acontece quando duas pessoas mexeram no mesmo trecho de código. Não entre em pânico, é simples de resolver:

```bash
git checkout sua-branch
git merge main
```
O Git vai apontar quais arquivos têm conflito. Abra cada um, escolha o que manter, salve e depois:

```bash
git add .
git commit -m "resolve conflito com a main"
git push origin sua-branch
```

QUALQUER DUVIDA CHAMAR NO WHATS PARA NOS AJUDARMOS E EXPLICAR MELHOR
