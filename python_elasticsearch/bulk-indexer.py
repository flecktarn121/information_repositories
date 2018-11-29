# Para poder usar la función print e imprimir sin saltos de línea
from __future__ import print_function

import json # Para poder trabajar con objetos JSON
import pprint # Para poder hacer uso de PrettyPrinter
import sys # Para poder usar exit

from elasticsearch import Elasticsearch
from elasticsearch import helpers

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
    # Leemos el fichero en grandes bloques
    #
    tamano = 8*1024*1024 # Para leer 8MB, tamaño estimado de manera experimental
    fh = open("mentalhealth-subreddits.json", 'rt')
    lineas = fh.readlines(tamano)
    while lineas:
      procesarLineas(lineas)
#      sys.exit()
      lineas = fh.readlines(tamano)
    fh.close()

# Aquí indexaremos los documentos en bloques
def procesarLineas(lineas):
  jsonvalue = []

  for linea in lineas:
    datos = json.loads(linea) # Para acceder al diccionario

    ident = datos["id"]

    datos["_index"] = "reddit-mentalhealth"
    datos["_type"] = "post"
    datos["_id"] = ident

    jsonvalue.append(datos)

  # pp = pprint.PrettyPrinter(indent=2)
  # pp.pprint(jsonvalue)

  num_elementos = len(jsonvalue)
  resultado = helpers.bulk(es,jsonvalue,chunk_size=num_elementos,request_timeout=200)

  # Habría que procesar el resultado para ver que todo vaya bien...

  print('.',end='')

if __name__ == '__main__':
    main()
