**Banco de dados: Postgres**

**Backend: Java Spring**

**Frontend: React JS**

# Funcionalidade 1

Fiz a criação de livros usando a API do Google Books para pré-popular os dados no banco de dados. O usuário pode pesquisar pelo livro desejado preenchendo alguns inputs (nenhum deles obrigatórios) que estão no topo da página, 
listando tudo que for encontrado na API. No fim da página, tem dois botões para mudar de página (cada página pode conter só até 10 livros) e um para salvar. O usuário pode selecionar um card de livro e clicar em salvar no fim da página, 
fazendo com que o livro seja salvo no banco de dados e o usuário seja redirecionado para a página inicial, onde ele pode ver todos os livros criados no banco de dados.

# Funcionalidade 2

Ao clicar em qualquer dos livros disponíveis, abre uma modal cheia de inputs preenchidos com informações do livro. Alterando qualquer das informações, tem um botão de salvar no canto inferior direito da modal para salvá-las.
Depois de clicar nele, a página recarrega e as informações já aparecem atualizadas. Nessa mesma modal também tem um botão de excluir, que apaga o livro do banco de dados

# Funcionalidade 3
Na modal de edição de livros, ao clicar no botão "Entradas" o usuário é redirecionado para uma página contendo detalhes do livro e entradas relacionadas. Essas entradas seriam vezes que o usuário leu o livro, contendo a data, quantos porcento ele já leu do livro, e uma resenha do que ele achou. Sempre que for criada uma entrada com a checkbox "Terminou o livro?" marcada, a sessão de leitura vai ser finalizada, e qualquer outras entradas criadas após isso serão parte de outra sessão.
