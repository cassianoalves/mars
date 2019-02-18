# Mars - Explorando Marte

Descrição da solução apresentada para o problema apresentado.

# Abordagem da Solução

Para uma solução usando uma API REST foi desenhado um processo de manejo
das sondas em que seja possível cadastrar os planaltos de exploração,
cadastrar as sondas nos planaltos e pode enviar sequëncias de comando para
cada sonda.

Além disso será possível consultar o estado de cada sonda.

Dado que essa solução exige algum tipo de persistência, iremos implementar
uma persistencia simples em memória.

## Definições de Domínio

| Termo        | No código | Descrição                                                                       |
|--------------|-----------|---------------------------------------------------------------------------------|
| Sonda        | Probe     |Veículo que será guiado pelos comandos sobre o planalto onde ela se encontra     |
| Planalto     | Plateau    |Área de exploração da sonda                                                      |

## Recursos REST

Definições dos serviços disponíveis. Em geral os serviços seguem as seguintes definições gerais:

* Os serviços de inserção de dados retornarão a URI do recurso criado no header *Location* da resposta.
* Cada sonda estará em apenas 1 planalto criado.
* Os objetos serão todos em formato JSON

### Planalto

**URIs**
```
POST /plateau       : Cria novo planalto. Campo 'id' no json, se enviado, é ignorado.
PUT  /plateau/{id}  : Altera o planalto 'id'. Campo 'id' no json, se enviado, é ignorado.
GET  /plateau/{id}  : Retorna o planalto 'id'.
GET  /plateau       : Retorna uma lista com todos os planaltos
```
**Objeto JSON**
```
{
   "id": "b4d5b85632abc338dfa1337e286e6f0e",
   "xSize": 5,
   "ySize": 10
}
```

### Sonda

**URIs**
```
POST /plateau/{plid}/probe/       : Cria nova sonda no planalto indicado (plid) na posição inicial indicada no json. Campo 'id' no json, se enviado, é ignorado.
GET  /plateau/{plid}/probe/{pbid} : Retorna sonda 'pbid' com sua posição atual.
GET  /plateau/{plid}/probe        : Retorna uma lista com todos as sondas no planalto 'plid'
```
**Objeto JSON**
```
{
   "id": "b4d5b85632abc338dfa1337e286e6f0e",
   "xPosition": 1,
   "yPosition": 3
}
```

### Comandos para a sonda

**URIs**
```
POST /plateau/{plid}/probe/{pbid}/move : Executa os movimentos enviados na lista de comandos do json.
```
**Objeto JSON**
```
[ "L", "M", "M", "M", "R", "M", "L", "M", "M" ]
```
*' "L" e "R" fazem a sonda virar 90 graus para a esquerda ou direita, respectivamente, sem mover a sonda. "M" faz com que a sonda mova-se para a frente um ponto da malha, mantendo a mesma direção. '*