# Repositorio
Faça clone do repositório normalmente. Configure a chave de ssh no github e confirme que foi adicionada. Esta configuração demora e não há como saber até que tente fazer um push e dê certo.

# Exercícios
Os exercicios estão na pasta de projetos_aulas/Projeto. O projeto ou projetos são forks do repositório oficial da Udacity. Adicione eles como submodules neste repositório. A forma mais rápida e comoda para realizar os exercicios é utilizar cada branch já criada nos repositorios. Este modo permite manter uma janela do Intellij e precisar somente de trocar de branch para cada exercicio, pois o projeto fica na raiz do repositorio. 

Cada projeto foi criado com versões do Java e Android diferente. Eu criei um patch para ser aplicado para em cada branch. O patch configura a versão do Android tools, Java e gradle. A aplicação do patch é simples, porém existe um detalhe, ao tentar importar para projetos diferentes, é necessário setar uma nova base para o patch, através do botão set new Base path. Depois, a dica é deixar o patch como shelf, assim, a cada novo exercicio para o mesmo projeto, é só fazer unshelf.
