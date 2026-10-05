# ArrayList — Ajout et modification des éléments

## 1. Objectif

Cette section présente les différentes opérations permettant d'ajouter et de
modifier des éléments dans une `ArrayList`.

Nous allons étudier :

- `add(E element)` ;
- `add(int index, E element)` ;
- `addFirst(E element)` ;
- `addLast(E element)` ;
- `addAll(Collection<? extends E> collection)` ;
- `addAll(int index, Collection<? extends E> collection)` ;
- `set(int index, E element)` ;
- la gestion des index ;
- les valeurs `null` ;
- les doublons ;
- les conséquences de ces opérations sur les performances.

`ArrayList` implémente toutes les opérations optionnelles de `List` et accepte
également les valeurs `null`. :chatgpt-content-reference{index="1"}

---

## 2. Ajouter un élément avec `add(E element)`

La méthode :

```java
add(E element)
```

ajoute un élément à la **fin** de la liste.

Exemple :

```java
ArrayList<String> technologies = new ArrayList<>();

technologies.add("Java");
technologies.add("Spring Boot");
technologies.add("React");
```

La liste contient :

```text
[Java, Spring Boot, React]
```

Chaque nouvel élément est ajouté après le dernier élément existant.

Conceptuellement :

```text
Avant :

[Java][Spring Boot][React]

add("Angular")

                   ↓

Après :

[Java][Spring Boot][React][Angular]
```

La méthode retourne un `boolean`.

Pour une `ArrayList`, l'ajout réussit et `add(E)` retourne :

```text
true
```

La documentation Java 21 indique explicitement que `ArrayList.add(E)` ajoute
l'élément à la fin et retourne `true`. :chatgpt-content-reference{index="2"}

---

## 3. Complexité de `add(E)`

L'ajout en fin de liste est une opération en :

```text
O(1) amorti
```

Cela signifie que la majorité des ajouts ont un coût constant.

Cependant, lorsque le tableau interne ne possède plus suffisamment de capacité,
`ArrayList` doit augmenter son stockage interne.

Conceptuellement :

```text
[Java][Spring][React]
        capacité pleine
              │
              ▼
    création d'un stockage
         plus important
              │
              ▼
     copie des références
              │
              ▼
       ajout de l'élément
```

Un ajout particulier peut donc être plus coûteux.

Sur une longue série d'ajouts, le coût moyen reste néanmoins constant amorti.
La Javadoc Java 21 garantit ce coût amorti sans imposer la stratégie exacte de
croissance du tableau interne. :chatgpt-content-reference{index="3"}

---

## 4. Ajouter un élément à une position précise

La méthode :

```java
add(int index, E element)
```

permet d'insérer un élément à une position déterminée.

Exemple :

```java
ArrayList<String> technologies = new ArrayList<>();

technologies.add("Java");
technologies.add("React");

technologies.add(1, "Spring Boot");
```

Avant :

```text
index      0       1

        [Java]  [React]
```

Après :

```text
index      0          1          2

        [Java] [Spring Boot] [React]
```

L'élément précédemment situé à l'index `1`, ainsi que les éléments suivants,
sont déplacés vers la droite. :chatgpt-content-reference{index="4"}

---

## 5. Index valides pour `add(index, element)`

Pour l'insertion, l'index doit respecter :

```text
0 <= index <= size()
```

C'est un point important.

Si la liste contient :

```text
[Java, Spring Boot, React]
```

sa taille est :

```text
3
```

Les index valides pour une insertion sont alors :

```text
0
1
2
3
```

L'index :

```text
3
```

est valide car il correspond à une insertion à la fin.

Exemple :

```java
technologies.add(technologies.size(), "Angular");
```

Résultat :

```text
[Java, Spring Boot, React, Angular]
```

En revanche :

```java
technologies.add(4, "Angular");
```

sur une liste de taille `3` provoque :

```text
IndexOutOfBoundsException
```

La condition définie par l'API est :

```text
index < 0 || index > size()
``` :chatgpt-content-reference{index="5"}


---

## 6. Coût d'une insertion au milieu

`ArrayList` utilise un tableau.

Lorsqu'un élément est inséré au milieu, les éléments suivants doivent être
décalés.

Exemple :

```text
Avant :

[Java][React][Angular]

Insertion de Spring à l'index 1

           ↓

[Java][ ][React][Angular]

           ↓

[Java][Spring][React][Angular]
```

Plus l'insertion se fait près du début d'une grande liste, plus le nombre
d'éléments potentiellement déplacés est important.

Une insertion arbitraire dans une `ArrayList` est donc généralement une
opération en :

```text
O(n)
```

Cela explique pourquoi une `ArrayList` est particulièrement adaptée aux
ajouts en fin de liste, mais moins aux insertions répétées au début ou au
milieu de très grandes listes. La documentation générale d'`ArrayList`
indique que les opérations autres que celles explicitement constantes ou
amorties sont globalement linéaires. :chatgpt-content-reference{index="6"}

---

# 7. `addFirst(E element)` — Java 21

Depuis Java 21, `List` fait partie des collections séquencées et permet
notamment d'ajouter directement un élément au début.

Avec `ArrayList` :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of("Spring Boot", "React")
        );

technologies.addFirst("Java");
```

Résultat :

```text
[Java, Spring Boot, React]
```

`addFirst()` a été ajouté à l'API avec Java 21. :chatgpt-content-reference{index="7"}

Conceptuellement, pour une liste :

```text
[Spring][React][Angular]

addFirst("Java")

        ↓

[Java][Spring][React][Angular]
```

---

## 7.1 Performance de `addFirst()` avec ArrayList

Même si `addFirst()` rend le code plus expressif, la structure interne
d'`ArrayList` reste un tableau.

Il faut donc déplacer les éléments déjà présents.

Conceptuellement :

```text
Avant :

[Java][Spring][React]

Ajout au début :

[ ][Java][Spring][React]

Puis :

[Angular][Java][Spring][React]
```

Le coût est donc généralement :

```text
O(n)
```

`addFirst()` ne transforme pas `ArrayList` en structure optimisée pour les
insertions en tête.

La méthode améliore surtout **l'expressivité de l'API**.

---

# 8. `addLast(E element)` — Java 21

Java 21 fournit également :

```java
addLast(E element)
```

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>();

technologies.addLast("Java");
technologies.addLast("Spring Boot");
```

Résultat :

```text
[Java, Spring Boot]
```

Avec une `ArrayList`, cette opération ajoute un élément à la fin de la
collection. :chatgpt-content-reference{index="8"}

Dans ce contexte :

```java
technologies.add("React");
```

et :

```java
technologies.addLast("React");
```

expriment tous deux un ajout en fin de liste.

La différence principale est sémantique :

```java
add(...)
```

est l'opération historique de `Collection`/`List`, tandis que :

```java
addLast(...)
```

exprime explicitement que l'on travaille sur l'extrémité finale d'une
collection ordonnée.

---

# 9. Ajouter plusieurs éléments avec `addAll()`

La méthode :

```java
addAll(Collection<? extends E> collection)
```

permet d'ajouter tous les éléments d'une autre collection à la fin de
l'`ArrayList`.

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>();

technologies.add("Java");

List<String> frameworks =
        List.of("Spring Boot", "React", "Angular");

technologies.addAll(frameworks);
```

Résultat :

```text
[Java, Spring Boot, React, Angular]
```

Les éléments sont ajoutés dans l'ordre fourni par l'itérateur de la collection
source. :chatgpt-content-reference{index="9"}

---

## 9.1 Valeur retournée par `addAll()`

`addAll()` retourne un `boolean`.

```java
boolean changed =
        technologies.addAll(frameworks);
```

Le résultat indique si la liste a été modifiée.

Par exemple, lorsqu'une collection non vide est ajoutée :

```text
true
```

est retourné.

---

## 9.2 Collection `null`

Ce code :

```java
technologies.addAll(null);
```

provoque :

```text
NullPointerException
```

La collection elle-même ne peut donc pas être `null`. :chatgpt-content-reference{index="10"}

---

# 10. Ajouter plusieurs éléments à un index

Il existe également :

```java
addAll(
    int index,
    Collection<? extends E> collection
)
```

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of("Java", "Angular")
        );

List<String> technologiesToInsert =
        List.of("Spring Boot", "React");

technologies.addAll(
        1,
        technologiesToInsert
);
```

Résultat :

```text
[Java, Spring Boot, React, Angular]
```

Les éléments existants situés à partir de l'index indiqué sont décalés vers
la droite. :chatgpt-content-reference{index="11"}

---

## 10.1 Index valide pour `addAll(index, collection)`

Comme pour :

```java
add(index, element)
```

l'index doit respecter :

```text
0 <= index <= size()
```

Un index égal à :

```java
size()
```

est donc autorisé.

Il correspond à l'ajout de la collection à la fin de la liste.

Un index invalide provoque :

```text
IndexOutOfBoundsException
``` :chatgpt-content-reference{index="12"}


---

# 11. Attention à `addAll()` avec la liste elle-même

Il ne faut pas écrire du code dont le fonctionnement dépend de :

```java
list.addAll(list);
```

La documentation d'`ArrayList` précise que le comportement de `addAll()` est
indéfini lorsque la collection passée en argument est modifiée pendant
l'opération, ce qui inclut le cas où la collection source est la liste
elle-même et qu'elle n'est pas vide. :chatgpt-content-reference{index="13"}

Même si une implémentation particulière semble produire un résultat
prévisible, le contrat public ne doit pas être interprété comme une garantie
pour ce cas.

---

# 12. Modifier un élément avec `set()`

La méthode :

```java
set(int index, E element)
```

remplace l'élément actuellement présent à un index donné.

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of(
                        "Java",
                        "Spring",
                        "React"
                )
        );

technologies.set(
        1,
        "Spring Boot"
);
```

Avant :

```text
[Java, Spring, React]
```

Après :

```text
[Java, Spring Boot, React]
```

La taille de la liste reste inchangée :

```text
Avant : size = 3
Après : size = 3
```

---

## 12.1 Valeur retournée par `set()`

Une particularité importante est que `set()` retourne **l'ancien élément**.

Exemple :

```java
String previous =
        technologies.set(
                1,
                "Spring Boot"
        );
```

Si la liste était :

```text
[Java, Spring, React]
```

alors :

```text
previous = "Spring"
```

et la liste devient :

```text
[Java, Spring Boot, React]
```

La Javadoc Java 21 définit explicitement ce comportement. :chatgpt-content-reference{index="14"}

---

# 13. Index valide pour `set()`

Contrairement à :

```java
add(index, element)
```

`set()` ne permet pas d'utiliser :

```text
index == size()
```

L'élément doit déjà exister.

La condition est :

```text
0 <= index < size()
```

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(
                List.of("Java", "React")
        );
```

Ici :

```text
size = 2
```

Les index valides pour `set()` sont :

```text
0
1
```

Ce code :

```java
technologies.set(
        2,
        "Angular"
);
```

provoque :

```text
IndexOutOfBoundsException
``` :chatgpt-content-reference{index="15"}


---

# 14. Différence fondamentale entre `add()` et `set()`

Considérons :

```text
[Java, React]
```

Avec :

```java
list.add(
        1,
        "Spring Boot"
);
```

on obtient :

```text
[Java, Spring Boot, React]
```

La taille passe de :

```text
2 → 3
```

En revanche :

```java
list.set(
        1,
        "Spring Boot"
);
```

donne :

```text
[Java, Spring Boot]
```

La taille reste :

```text
2
```

Donc :

```text
add()
   ↓
insère un nouvel élément

set()
   ↓
remplace un élément existant
```

Cette distinction est fondamentale.

---

# 15. `set()` et modification structurelle

Dans `ArrayList`, remplacer un élément avec :

```java
set(index, element)
```

ne change pas la taille de la liste.

La documentation d'`ArrayList` précise qu'une modification structurelle est
une opération qui ajoute ou supprime des éléments, ou redimensionne
explicitement le tableau interne ; le simple remplacement d'un élément n'est
pas une modification structurelle. :chatgpt-content-reference{index="16"}

Ainsi :

```java
list.add("Java");
```

est une modification structurelle.

Alors que :

```java
list.set(0, "Spring");
```

ne l'est pas.

Cette distinction deviendra particulièrement importante lorsque nous
étudierons les itérateurs et `ConcurrentModificationException`.

---

# 16. Valeurs `null`

`ArrayList` accepte les valeurs `null`. :chatgpt-content-reference{index="17"}

Par exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>();

technologies.add(null);
technologies.add("Java");
```

Résultat :

```text
[null, Java]
```

Il est également possible de remplacer une valeur par `null` :

```java
technologies.set(
        1,
        null
);
```

Résultat :

```text
[null, null]
```

Le fait qu'une API autorise `null` ne signifie cependant pas qu'il est
toujours souhaitable de l'utiliser.

Dans du code métier, l'utilisation de `null` doit rester intentionnelle afin
d'éviter des traitements inutiles et des `NullPointerException`.

---

# 17. Doublons

Contrairement à un `Set`, une `List` autorise généralement les doublons.

`ArrayList` accepte donc :

```java
ArrayList<String> technologies =
        new ArrayList<>();

technologies.add("Java");
technologies.add("Java");
technologies.add("Java");
```

Résultat :

```text
[Java, Java, Java]
```

Les trois éléments occupent des positions différentes :

```text
index 0 → Java
index 1 → Java
index 2 → Java
```

C'est une propriété normale d'une `List`.

---

# 18. Résumé des index

Il est important de retenir la différence entre insertion et remplacement.

| Opération | Index valide |
|---|---|
| `add(index, element)` | `0 <= index <= size()` |
| `addAll(index, collection)` | `0 <= index <= size()` |
| `set(index, element)` | `0 <= index < size()` |

La raison est simple :

```text
add / addAll
    ↓
peuvent ajouter après le dernier élément

set
    ↓
doit remplacer un élément existant
```

---

# 19. Résumé des principales opérations

| Méthode | Effet | Taille modifiée ? |
|---|---|---|
| `add(E)` | Ajoute à la fin | Oui |
| `add(index, E)` | Insère à une position | Oui |
| `addFirst(E)` | Ajoute au début | Oui |
| `addLast(E)` | Ajoute à la fin | Oui |
| `addAll(Collection)` | Ajoute plusieurs éléments à la fin | Oui si collection non vide |
| `addAll(index, Collection)` | Insère plusieurs éléments | Oui si collection non vide |
| `set(index, E)` | Remplace un élément | Non |

---

# 20. Complexité générale

Pour une `ArrayList`, il faut toujours garder à l'esprit qu'elle repose sur
un tableau redimensionnable.

| Opération | Complexité générale |
|---|---:|
| `add(element)` | `O(1)` amorti |
| `addLast(element)` | `O(1)` amorti |
| `addFirst(element)` | `O(n)` |
| `add(index, element)` | `O(n)` dans le cas général |
| `set(index, element)` | `O(1)` |

Pour `addAll`, le coût dépend notamment :

- du nombre d'éléments ajoutés ;
- de la position d'insertion ;
- de la nécessité éventuelle d'augmenter la capacité ;
- du nombre d'éléments existants devant être déplacés.

Il vaut mieux comprendre ces facteurs que mémoriser une formule simpliste.

---

# 21. Bonnes pratiques

Pour ajouter simplement en fin :

```java
list.add(element);
```

est généralement le choix naturel.

Utiliser :

```java
addFirst()
```

ou :

```java
addLast()
```

lorsque la notion d'extrémité améliore réellement la lisibilité du code.

Éviter les insertions répétées au début d'une très grande `ArrayList` lorsqu'une
autre structure de données serait mieux adaptée.

Utiliser :

```java
set()
```

uniquement lorsqu'un élément existe déjà et doit être remplacé.

Ne pas utiliser `set()` comme substitut à `add()`.

---

# 22. À retenir

`ArrayList` permet plusieurs formes d'ajout :

```java
add(element);
add(index, element);
addFirst(element);
addLast(element);
addAll(collection);
addAll(index, collection);
```

Depuis Java 21, `addFirst()` et `addLast()` permettent d'exprimer directement
les opérations sur les extrémités de la liste.

Pour modifier un élément existant :

```java
set(index, element);
```

La distinction essentielle est :

```text
add
    → ajoute

set
    → remplace
```

Pour les index :

```text
add(index, element)

0 <= index <= size()
```

alors que :

```text
set(index, element)

0 <= index < size()
```

Enfin, le choix d'une méthode ne doit pas seulement être guidé par sa
syntaxe : la structure interne d'`ArrayList` implique que les insertions au
début ou au milieu peuvent nécessiter le déplacement de nombreux éléments.