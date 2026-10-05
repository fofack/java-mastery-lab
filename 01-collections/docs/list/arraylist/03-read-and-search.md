# ArrayList — Lecture et recherche

## 1. Objectif

Cette section présente les principales opérations permettant de lire et de
rechercher des éléments dans une `ArrayList`.

Nous allons étudier :

- `get(int index)` ;
- `getFirst()` ;
- `getLast()` ;
- `contains(Object element)` ;
- `containsAll(Collection<?> collection)` ;
- `indexOf(Object element)` ;
- `lastIndexOf(Object element)` ;
- le fonctionnement de la recherche avec `equals()` ;
- le comportement avec les doublons ;
- le comportement avec les valeurs `null` ;
- les principales implications en matière de performances.

---

## 2. Lire un élément avec `get(int index)`

La méthode :

```java
get(int index)
```

retourne l'élément situé à l'index spécifié.

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"
                )
        );

String technology = technologies.get(1);
```

La variable :

```java
technology
```

contient :

```text
Spring Boot
```

Les index commencent à `0`.

La liste :

```text
[Java, Spring Boot, React]
```

peut donc être représentée ainsi :

```text
 index      0          1          2
          ┌──────┬─────────────┬───────┐
          │ Java │ Spring Boot │ React │
          └──────┴─────────────┴───────┘
```

---

## 3. Index valides pour `get()`

Pour :

```java
get(index)
```

l'index doit respecter :

```text
0 <= index < size()
```

Considérons :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"
                )
        );
```

La taille est :

```text
3
```

Les index valides sont donc :

```text
0
1
2
```

Ce code est valide :

```java
technologies.get(2);
```

et retourne :

```text
React
```

En revanche :

```java
technologies.get(3);
```

provoque :

```text
IndexOutOfBoundsException
```

car :

```text
index == size()
```

n'est pas valide pour une opération de lecture.

Un index négatif provoque également :

```text
IndexOutOfBoundsException
```

Exemple :

```java
technologies.get(-1);
```

---

## 4. Performance de `get()`

`ArrayList` repose sur un tableau redimensionnable.

L'accès à un élément par son index peut donc être effectué directement.

Conceptuellement :

```text
get(2)

          accès direct
              │
              ▼
┌──────┬─────────────┬───────┬─────────┐
│ Java │ Spring Boot │ React │ Angular │
└──────┴─────────────┴───────┴─────────┘
                          ▲
                          │
                        index 2
```

La complexité de :

```java
get(index)
```

est :

```text
O(1)
```

C'est l'un des principaux avantages d'`ArrayList`.

Cette caractéristique est particulièrement intéressante lorsqu'une application
effectue de nombreux accès aléatoires par index.

---

# 5. `getFirst()` — Java 21

Depuis Java 21, il est possible de récupérer directement le premier élément
d'une liste avec :

```java
getFirst()
```

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"
                )
        );

String first = technologies.getFirst();
```

Résultat :

```text
Java
```

Cela correspond conceptuellement à :

```java
technologies.get(0);
```

mais :

```java
getFirst()
```

exprime plus clairement l'intention :

```text
récupérer le premier élément
```

plutôt que :

```text
récupérer l'élément situé à l'index 0
```

---

## 6. `getFirst()` sur une liste vide

Considérons :

```java
ArrayList<String> technologies =
        new ArrayList<>();
```

L'appel :

```java
technologies.getFirst();
```

provoque :

```text
NoSuchElementException
```

Il ne retourne pas :

```text
null
```

Le fait qu'une `ArrayList` puisse contenir `null` ne signifie donc pas que
`getFirst()` retourne `null` lorsqu'elle est vide.

Il faut distinguer :

```text
liste vide
```

de :

```text
liste contenant null
```

Par exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>();

technologies.add(null);

String first = technologies.getFirst();
```

Ici :

```text
first == null
```

car la liste contient réellement un élément dont la valeur est `null`.

---

# 7. `getLast()` — Java 21

La méthode :

```java
getLast()
```

retourne le dernier élément de la liste.

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"
                )
        );

String last = technologies.getLast();
```

Résultat :

```text
React
```

Conceptuellement, pour une liste non vide :

```java
getLast()
```

correspond à :

```java
get(size() - 1);
```

`getLast()` améliore cependant la lisibilité du code lorsque l'intention est
explicitement de récupérer le dernier élément.

---

## 8. `getLast()` sur une liste vide

Comme `getFirst()`, l'appel :

```java
getLast()
```

sur une liste vide provoque :

```text
NoSuchElementException
```

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>();

technologies.getLast();
```

Il est donc possible de vérifier préalablement :

```java
if (!technologies.isEmpty()) {
    String last = technologies.getLast();
}
```

lorsque l'absence d'élément est une situation normale de l'application.

---

# 9. Rechercher avec `contains()`

La méthode :

```java
contains(Object element)
```

permet de vérifier si au moins une occurrence d'un élément est présente dans
la liste.

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React"
                )
        );

boolean containsJava =
        technologies.contains("Java");
```

Résultat :

```text
true
```

Alors que :

```java
boolean containsAngular =
        technologies.contains("Angular");
```

retourne :

```text
false
```

`contains()` répond donc à la question :

```text
Cet élément existe-t-il au moins une fois dans la liste ?
```

---

# 10. Comment `contains()` compare-t-il les objets ?

La recherche n'est pas basée sur l'identité mémoire des objets.

Elle utilise leur égalité logique.

Formellement, la recherche correspond à une comparaison basée sur :

```java
Objects.equals(searchedElement, currentElement)
```

`Objects.equals()` permet notamment de gérer correctement les valeurs `null`.

Pour les objets non nuls, cela revient essentiellement à utiliser leur
méthode :

```java
equals()
```

Cela a une conséquence très importante pour les objets métier.

---

## 11. Recherche d'objets métier et `equals()`

Considérons :

```java
class Developer {

    private final String name;

    Developer(String name) {
        this.name = name;
    }
}
```

Puis :

```java
Developer first =
        new Developer("Alice");

Developer second =
        new Developer("Alice");
```

Même si les deux objets contiennent :

```text
Alice
```

ils ne seront considérés comme égaux que si leur contrat d'égalité le permet.

Pour les objets métier utilisés dans des collections, il est donc important
de comprendre et de définir correctement :

```java
equals()
```

et, de manière générale :

```java
hashCode()
```

lorsque la sémantique métier l'exige.

Sinon, une recherche comme :

```java
developers.contains(
        new Developer("Alice")
);
```

peut retourner :

```text
false
```

alors qu'un développeur portant ce nom semble visuellement présent.

La collection respecte le contrat d'égalité des objets qu'elle contient.

---

# 12. `contains()` et `null`

`ArrayList` autorise les éléments `null`.

Il est donc possible de rechercher :

```java
null
```

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>();

technologies.add("Java");
technologies.add(null);
technologies.add("React");

boolean containsNull =
        technologies.contains(null);
```

Résultat :

```text
true
```

Si aucun élément `null` n'est présent :

```java
technologies.contains(null);
```

retourne :

```text
false
```

---

# 13. Performance de `contains()`

Contrairement à :

```java
get(index)
```

`contains()` ne connaît pas la position de l'élément recherché.

`ArrayList` doit rechercher l'élément dans son contenu.

Conceptuellement :

```text
contains("React")

Java
 │
 ▼
pas égal

Spring Boot
 │
 ▼
pas égal

React
 │
 ▼
trouvé
```

Dans le pire cas, il peut être nécessaire de parcourir toute la liste.

La complexité est donc :

```text
O(n)
```

Cela devient important lorsqu'une application effectue énormément de recherches
d'appartenance dans une grande collection.

Dans ce type de situation, une autre structure de données peut parfois être
plus appropriée.

---

# 14. Vérifier plusieurs éléments avec `containsAll()`

La méthode :

```java
containsAll(Collection<?> collection)
```

permet de vérifier si la liste contient **tous** les éléments d'une autre
collection.

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React",
                        "Angular"
                )
        );

boolean result =
        technologies.containsAll(
                List.of(
                        "Java",
                        "React"
                )
        );
```

Résultat :

```text
true
```

car les deux éléments recherchés sont présents.

En revanche :

```java
boolean result =
        technologies.containsAll(
                List.of(
                        "Java",
                        "Flutter"
                )
        );
```

retourne :

```text
false
```

car :

```text
Flutter
```

n'est pas présent.

---

## 15. `containsAll()` avec une collection vide

Considérons :

```java
technologies.containsAll(
        List.of()
);
```

Le résultat est :

```text
true
```

Une collection ne contenant aucun élément n'impose aucune condition qui puisse
échouer.

Autrement dit, toute collection contient tous les éléments de la collection
vide.

---

## 16. Performance de `containsAll()`

`containsAll()` peut nécessiter plusieurs recherches dans la liste.

Conceptuellement :

```text
pour chaque élément recherché
        │
        ▼
effectuer une recherche contains()
```

Le coût dépend donc notamment :

- de la taille de l'`ArrayList` ;
- du nombre d'éléments recherchés ;
- de leur position ;
- du résultat des comparaisons `equals()`.

Il ne faut pas considérer `containsAll()` comme une opération à coût constant.

Pour de grandes collections utilisées principalement pour des recherches
d'appartenance, il peut être intéressant d'étudier une structure telle qu'un
`Set`.

---

# 17. Rechercher la première occurrence avec `indexOf()`

La méthode :

```java
indexOf(Object element)
```

retourne l'index de la **première occurrence** de l'élément recherché.

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React",
                        "Java"
                )
        );

int index =
        technologies.indexOf("Java");
```

Résultat :

```text
0
```

Même si `Java` apparaît aussi à l'index :

```text
3
```

`indexOf()` s'arrête sur la première occurrence.

Conceptuellement :

```text
 index      0          1          2       3
          ┌──────┬─────────────┬───────┬──────┐
          │ Java │ Spring Boot │ React │ Java │
          └──────┴─────────────┴───────┴──────┘
             ▲
             │
        première occurrence
```

---

# 18. Élément absent avec `indexOf()`

Si l'élément n'est pas trouvé :

```java
int index =
        technologies.indexOf("Flutter");
```

le résultat est :

```text
-1
```

`indexOf()` ne lance donc pas une exception lorsqu'un élément est absent.

Il retourne :

```text
-1
```

Il faut donc éviter :

```java
String value =
        technologies.get(
                technologies.indexOf("Flutter")
        );
```

car si l'élément est absent, cela revient à appeler :

```java
technologies.get(-1);
```

et provoquera :

```text
IndexOutOfBoundsException
```

Une utilisation plus sûre est :

```java
int index =
        technologies.indexOf("Flutter");

if (index >= 0) {
    String value =
            technologies.get(index);
}
```

---

# 19. `indexOf()` et `null`

Puisque `ArrayList` accepte `null`, il est également possible de rechercher
la première occurrence de `null`.

Exemple :

```java
ArrayList<String> values =
        new ArrayList<>();

values.add("Java");
values.add(null);
values.add("React");
values.add(null);

int index =
        values.indexOf(null);
```

Résultat :

```text
1
```

car l'élément `null` apparaît pour la première fois à l'index `1`.

---

# 20. Rechercher la dernière occurrence avec `lastIndexOf()`

La méthode :

```java
lastIndexOf(Object element)
```

retourne l'index de la **dernière occurrence** de l'élément.

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of(
                        "Java",
                        "Spring Boot",
                        "React",
                        "Java"
                )
        );

int index =
        technologies.lastIndexOf("Java");
```

Résultat :

```text
3
```

Conceptuellement :

```text
 index      0          1          2       3
          ┌──────┬─────────────┬───────┬──────┐
          │ Java │ Spring Boot │ React │ Java │
          └──────┴─────────────┴───────┴──────┘
                                          ▲
                                          │
                                  dernière occurrence
```

---

# 21. Élément absent avec `lastIndexOf()`

Comme `indexOf()`, la méthode :

```java
lastIndexOf()
```

retourne :

```text
-1
```

si aucun élément correspondant n'est trouvé.

Exemple :

```java
int index =
        technologies.lastIndexOf("Flutter");
```

Résultat :

```text
-1
```

---

# 22. `lastIndexOf()` et `null`

Exemple :

```java
ArrayList<String> values =
        new ArrayList<>();

values.add(null);
values.add("Java");
values.add(null);
values.add("React");

int index =
        values.lastIndexOf(null);
```

Résultat :

```text
2
```

car la dernière occurrence de `null` se trouve à l'index `2`.

---

# 23. `indexOf()` vs `lastIndexOf()`

Considérons :

```text
[Java, Spring, Java, React, Java]
```

Alors :

```java
list.indexOf("Java");
```

retourne :

```text
0
```

et :

```java
list.lastIndexOf("Java");
```

retourne :

```text
4
```

Donc :

```text
indexOf()
    ↓
première occurrence


lastIndexOf()
    ↓
dernière occurrence
```

Ces deux méthodes sont particulièrement utiles car une `List` autorise les
doublons.

---

# 24. `contains()` vs `indexOf()`

Ces deux méthodes répondent à des questions différentes.

Si l'on veut uniquement savoir :

```text
Est-ce que l'élément existe ?
```

utiliser :

```java
contains(element);
```

Exemple :

```java
if (technologies.contains("Java")) {
    // ...
}
```

Si l'on a besoin de connaître sa position :

```java
int index =
        technologies.indexOf("Java");
```

Il est inutile d'effectuer :

```java
if (technologies.contains("Java")) {
    int index =
            technologies.indexOf("Java");
}
```

car cela peut provoquer deux parcours de la liste.

On peut directement écrire :

```java
int index =
        technologies.indexOf("Java");

if (index >= 0) {
    // element found
}
```

---

# 25. Attention aux recherches répétées

Considérons une très grande liste :

```java
ArrayList<User> users = ...;
```

Puis :

```java
for (User user : otherUsers) {
    if (users.contains(user)) {
        // ...
    }
}
```

Chaque appel à :

```java
contains()
```

peut nécessiter un parcours de l'`ArrayList`.

Des recherches répétées sur de grandes collections peuvent donc devenir
coûteuses.

Dans ce genre de scénario, il peut être pertinent de se demander si le besoin
réel correspond davantage à :

```text
une liste ordonnée
```

ou à :

```text
une structure optimisée pour les recherches d'appartenance
```

Le choix de la structure de données doit dépendre du type d'opérations
dominantes.

---

# 26. Résumé des exceptions et valeurs spéciales

| Opération        | Cas            | Résultat                    |
| ---------------- | -------------- | --------------------------- |
| `get(index)`     | index valide   | élément                     |
| `get(index)`     | index invalide | `IndexOutOfBoundsException` |
| `getFirst()`     | liste non vide | premier élément             |
| `getFirst()`     | liste vide     | `NoSuchElementException`    |
| `getLast()`      | liste non vide | dernier élément             |
| `getLast()`      | liste vide     | `NoSuchElementException`    |
| `contains(x)`    | trouvé         | `true`                      |
| `contains(x)`    | absent         | `false`                     |
| `indexOf(x)`     | trouvé         | premier index               |
| `indexOf(x)`     | absent         | `-1`                        |
| `lastIndexOf(x)` | trouvé         | dernier index               |
| `lastIndexOf(x)` | absent         | `-1`                        |

---

# 27. Complexité générale

| Opération                 |               Complexité avec `ArrayList` |
| ------------------------- | ----------------------------------------: |
| `get(index)`              |                                    `O(1)` |
| `getFirst()`              |                                    `O(1)` |
| `getLast()`               |                                    `O(1)` |
| `contains(element)`       |                                    `O(n)` |
| `indexOf(element)`        |                                    `O(n)` |
| `lastIndexOf(element)`    |                                    `O(n)` |
| `containsAll(collection)` | dépend du nombre de recherches effectuées |

`ArrayList` est donc particulièrement efficace lorsque l'on connaît l'index
de l'élément recherché.

Elle est moins adaptée aux scénarios dans lesquels l'opération dominante
consiste à rechercher continuellement l'existence d'éléments dans une très
grande collection.

---

# 28. Bonnes pratiques

Utiliser :

```java
get(index)
```

lorsque l'index est connu et valide.

Utiliser :

```java
getFirst()
```

et :

```java
getLast()
```

lorsque l'intention métier est réellement de récupérer une extrémité de la
liste.

Utiliser :

```java
contains()
```

lorsque seule l'existence de l'élément est importante.

Utiliser :

```java
indexOf()
```

ou :

```java
lastIndexOf()
```

lorsque la position est nécessaire.

Toujours prendre en compte le résultat :

```text
-1
```

avant d'utiliser l'index retourné.

Pour les objets métier, s'assurer que leur contrat :

```java
equals()
```

correspond réellement à la notion d'égalité attendue par l'application.

Éviter les recherches linéaires répétées sur de très grandes listes lorsque
le besoin pourrait être mieux satisfait par une autre structure de données.

---

# 29. À retenir

`ArrayList` fournit un accès très efficace par index :

```java
get(index);
```

avec une complexité :

```text
O(1)
```

Java 21 permet également d'exprimer directement l'accès aux extrémités :

```java
getFirst();
getLast();
```

Pour vérifier la présence d'un élément :

```java
contains(element);
```

Pour obtenir sa première position :

```java
indexOf(element);
```

Pour obtenir sa dernière position :

```java
lastIndexOf(element);
```

Lorsqu'un élément n'existe pas :

```java
indexOf()
lastIndexOf()
```

retournent :

```text
-1
```

La recherche repose sur l'égalité des objets, notamment à travers leur contrat
`equals()`.

Enfin, il faut retenir la principale différence de performance :

```text
accès par index
      ↓
     O(1)

recherche par valeur
      ↓
     O(n)
```

Cette distinction est essentielle pour choisir correctement `ArrayList`
selon les opérations dominantes d'une application.
