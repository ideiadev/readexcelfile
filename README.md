# readexcelfile
Leitura de Ficheiro Excel Para Tabela

Description : A Classe usa padrão, então se queres usar certifica-te de usar os mesmos padrões.

Uso Geral
  - Os atributos da classe POJO tem que estar ordenado conforme mencionado no ficheiro Excel.
  - É preciso colocar um constructor com a chave, se a chave for inteiro então passa o argumento como String e converte de String para Integer se for String atribui directo.
  - Na Classe Pojo não usar tipos primitivos, usar a classe ex: Int (não) - Integer (sim)
  - O File excel deve estar dentro da pasta web
  - Adicionar a biblioteca poi.jar no projecto.
  
Usar com EJB
  - É preciso declarar os atributos serialVersionUID e lists como ultimo. (77777777777)
   - como chamar
    1º ver exemplo do ficheiro IndexBean.java
    2º chamar o Managed Bean. ex:
       <f:metadata>
                <f:event type="preRenderView" listener="#{indexBean.init}"/>
       </f:metadata>
  
Usar sem EJB
  - usa uma interface GenericoDAO onde será incluido os metodos crud(create, update, delete...), e todos as entidades vão implementar essa interface.
  - como chamar
    * Na pagina Index incluir:
    <%
              new ReadWriteExcelFile().readXLSFile(application.getRealPath("endereco.xls"), 0, (GenericoDAO) new PaisDAO(), new Pais());
     %>
     
   



