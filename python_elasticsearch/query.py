# Para poder usar la función print e imprimir sin saltos de línea
from __future__ import print_function

import json # Para poder trabajar con objetos JSON
import pprint # Para poder hacer uso de PrettyPrinter
import sys # Para poder usar exit

from elasticsearch import Elasticsearch

def main():
    # Queremos imprimir bonito
    pp = pprint.PrettyPrinter(indent=2)

    # Nos conectamos por defecto a localhost:9200
    es = Elasticsearch()

    # La primera consulta que ejecutamos via GET sin especificar campos
    results = es.search(
        index="reddit-mentalhealth",
        q="diagnosed"
        )

    # pp.pprint(results)
    print(str(results["hits"]["total"]) + " resultados para la query q=\"diagnosed\"")

    # La segunda consulta ya especifica un campo de los documentos
    results = es.search(
        index="reddit-mentalhealth",
        q="selftext:diagn*"
        )

    # pp.pprint(results)
    print(str(results["hits"]["total"]) + " resultados para la query q=\"selftext:diagn*\"")

    # Otra consulta donde se hace el match sobre otro campo
    results = es.search(
        index="reddit-mentalhealth",
        q="subreddit:depression"
        )

    # pp.pprint(results)
    print(str(results["hits"]["total"]) + " resultados para la query q=\"subreddit:depression\"")

    # En ocasiones las consultas tienen que formalizarse en JSON
    results = es.search(
        index="reddit-mentalhealth",
        body = {
            "query": {
                "match": {
                    "selftext": {
                        "query": "diagnosed depression",
                        "operator": "and"
                    }
                }
            }
        }
        )

    # pp.pprint(results)
    print(str(results["hits"]["total"]) + " resultados para una query \"diagnosed AND depression\"")

    # Consulta que indica que solo nos interesa el número de resultados
    results = es.search(
        index = "reddit-mentalhealth",
        body = {
            "size": 0,
            "query": {
                "match": {
                    "subreddit": "depression"
                }
            }
        }
        )

    pp.pprint(results)

    # Preparar consultas con default_operator y docvalue_fields, por ejemplo
    # para obtener los textos únicamente para hacer data mining...
    #
    # ...

if __name__ == '__main__':
    main()
