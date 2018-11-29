# Para poder usar la función print e imprimir sin saltos de línea
from __future__ import print_function

import json # Para poder trabajar con objetos JSON
import pprint # Para poder hacer uso de PrettyPrinter
import sys # Para poder usar exit

from elasticsearch import Elasticsearch

def main():
    # Nos conectamos por defecto a localhost:9200
    global es
    es = Elasticsearch()

    # Creamos el índice
    #
    # Si no se crea explícitamente se crea al indexar el primer documento
    #
    # Debemos crearlo puesto que el mapeado por defecto (mapping) de algunos
    # campos, no es satisfactorio.
    #
    # ignore=400 hace que se ignore el error de índice ya existente
    #
    es.indices.create(index="reddit-mentalhealth",ignore=400)

    # Se especifican los tipos que no sirven por defecto...
    #
    argumentos={
      "properties": {
        "edited": {
          "type":"text"
        },
        "crosspost_parent_list.edited": {
          "type":"text"
        }
      }
    }
    es.indices.put_mapping(index="reddit-mentalhealth",doc_type="post",body=argumentos)

#    sys.exit()

    # Ahora se indexan los documentos.
    # Leemos el fichero línea a línea pues puede llegar a ser muy grande
    fh = open("mentalhealth-subreddits.json", 'rt')
    linea = fh.readline()
    while linea:
        procesarEntrada(linea)
        # sys.exit()
        linea = fh.readline()
    fh.close()

# Aquí indexaremos los documentos
def procesarEntrada(linea):
    datos=json.loads(linea) # Para acceder al diccionario

    # pp=pprint.PrettyPrinter(indent=2)
    # pp.pprint(datos)

    ident=datos["id"]

    es.index(
        index="reddit-mentalhealth",
        doc_type="post",
        id=ident,
        body=linea
    )

    # Habría que procesar el resultado para verificar que el documento ha sido
    # realmente indexado

    print('.',end='')

if __name__ == '__main__':
    main()
