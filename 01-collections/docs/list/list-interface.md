# L'interface `List<E>` en Java 21

## 1. Présentation

`List<E>` est une interface du Java Collections Framework représentant une collection **ordonnée** d'éléments.

Une liste permet notamment :

- de conserver un ordre déterminé ;
- d'accéder aux éléments par leur index ;
- d'insérer un élément à une position précise ;
- de rechercher la position d'un élément ;
- d'autoriser généralement les doublons ;
- selon l'implémentation, d'accepter des valeurs `null`.

Exemple :

```java
List<String> technologies = new ArrayList<>();

technologies.add("Java");
technologies.add("Spring Boot");
technologies.add("Java");
```

Résultat :

```text
[Java, Spring Boot, Java]
```

Les doublons sont autorisés.

---

# 2. Hiérarchie

En Java 21, la hiérarchie principale est :

```text
Iterable<E>
    │
    ▼
Collection<E>
    │
    ▼
SequencedCollection<E>
    │
    ▼
List<E>
```

`SequencedCollection` a été introduite avec Java 21.

Elle représente une collection possédant un ordre de parcours défini et permet notamment d'accéder aux deux extrémités de la collection.

---

# 3. Principales implémentations de List

Parmi les implémentations courantes :

```text
List<E>
│
├── ArrayList<E>
├── LinkedList<E>
├── Vector<E>
└── CopyOnWriteArrayList<E>
```

Le choix de l'implémentation dépend du type d'accès, des modifications effectuées, des besoins de concurrence et des contraintes de performance.

---

# 4. Création

Il est généralement préférable de déclarer une variable avec l'interface :

```java
List<String> developers = new ArrayList<>();
```

plutôt que :

```java
ArrayList<String> developers = new ArrayList<>();
```

Cela limite le couplage avec une implémentation particulière.

---

# 5. Taille et état

## `size()`

Retourne le nombre d'éléments.

```java
int size = developers.size();
```

---

## `isEmpty()`

Indique si la liste est vide.

```java
boolean empty = developers.isEmpty();
```

---

# 6. Ajout d'éléments

## `add(E element)`

Ajoute un élément à la fin de la liste.

```java
developers.add("Alice");
```

---

## `add(int index, E element)`

Ajoute un élément à une position donnée.

```java
developers.add(0, "Bob");
```

---

## `addAll(Collection<? extends E> collection)`

Ajoute tous les éléments d'une autre collection.

```java
developers.addAll(List.of("Alice", "Bob"));
```

---

## `addAll(int index, Collection<? extends E> collection)`

Insère une collection à partir d'une position précise.

```java
developers.addAll(1, List.of("Alice", "Bob"));
```

---

# 7. Opérations introduites via SequencedCollection

Java 21 permet d'utiliser directement les extrémités d'une liste.

## `addFirst(E element)`

Ajoute un élément au début.

```java
developers.addFirst("Alice");
```

---

## `addLast(E element)`

Ajoute un élément à la fin.

```java
developers.addLast("Bob");
```

---

## `getFirst()`

Retourne le premier élément.

```java
String first = developers.getFirst();
```

---

## `getLast()`

Retourne le dernier élément.

```java
String last = developers.getLast();
```

---

## `removeFirst()`

Supprime et retourne le premier élément.

```java
String removed = developers.removeFirst();
```

---

## `removeLast()`

Supprime et retourne le dernier élément.

```java
String removed = developers.removeLast();
```

---

## `reversed()`

Retourne une **vue** de la liste dans l'ordre inverse.

```java
List<String> reversed = developers.reversed();
```

Il est important de comprendre qu'il s'agit d'une vue et non nécessairement d'une nouvelle copie indépendante.

---

# 8. Lecture

## `get(int index)`

Retourne l'élément situé à une position donnée.

```java
String developer = developers.get(0);
```

Les index commencent à `0`.

---

# 9. Modification

## `set(int index, E element)`

Remplace l'élément situé à l'index indiqué.

```java
developers.set(0, "David");
```

---

# 10. Recherche

## `contains(Object element)`

Vérifie la présence d'un élément.

```java
boolean exists = developers.contains("Alice");
```

---

## `containsAll(Collection<?> collection)`

Vérifie que tous les éléments d'une autre collection sont présents.

```java
boolean containsAll =
        developers.containsAll(List.of("Alice", "Bob"));
```

---

## `indexOf(Object element)`

Retourne l'index de la première occurrence.

```java
int index = developers.indexOf("Alice");
```

Retourne `-1` si l'élément n'existe pas.

---

## `lastIndexOf(Object element)`

Retourne l'index de la dernière occurrence.

```java
int index = developers.lastIndexOf("Alice");
```

Cette méthode est particulièrement utile lorsqu'une liste contient des doublons.

---

# 11. Suppression

## `remove(int index)`

Supprime l'élément situé à l'index donné.

```java
developers.remove(0);
```

---

## `remove(Object element)`

Supprime la première occurrence correspondant à l'objet.

```java
developers.remove("Alice");
```

Il faut bien distinguer :

```java
remove(int)
```

et :

```java
remove(Object)
```

Cette différence est particulièrement importante avec les listes d'entiers.

Exemple :

```java
List<Integer> numbers = new ArrayList<>(List.of(10, 20, 30));

numbers.remove(1);
```

Ici, Java supprime l'élément à l'index `1`, donc `20`.

Pour supprimer la valeur `1`, on pourrait écrire :

```java
numbers.remove(Integer.valueOf(1));
```

---

## `removeAll(Collection<?> collection)`

Supprime tous les éléments également présents dans la collection fournie.

```java
developers.removeAll(List.of("Alice", "Bob"));
```

---

## `removeIf(Predicate<? super E> filter)`

Supprime les éléments correspondant à une condition.

```java
developers.removeIf(name -> name.startsWith("A"));
```

---

## `retainAll(Collection<?> collection)`

Ne conserve que les éléments également présents dans la collection fournie.

```java
developers.retainAll(List.of("Alice", "Bob"));
```

---

## `clear()`

Supprime tous les éléments.

```java
developers.clear();
```

---

# 12. Parcours

## `iterator()`

Retourne un `Iterator`.

```java
Iterator<String> iterator = developers.iterator();

while (iterator.hasNext()) {
    System.out.println(iterator.next());
}
```

---

## `listIterator()`

Retourne un `ListIterator`.

```java
ListIterator<String> iterator =
        developers.listIterator();
```

Contrairement à un `Iterator` classique, un `ListIterator` permet notamment un parcours bidirectionnel.

---

## `listIterator(int index)`

Commence le parcours à partir d'un index donné.

```java
ListIterator<String> iterator =
        developers.listIterator(2);
```

---

## `forEach()`

```java
developers.forEach(System.out::println);
```

---

# 13. Streams

## `stream()`

Création d'un flux séquentiel :

```java
developers.stream()
        .filter(name -> name.startsWith("A"))
        .forEach(System.out::println);
```

---

## `parallelStream()`

Création d'un flux pouvant être exécuté en parallèle :

```java
developers.parallelStream()
        .forEach(System.out::println);
```

Un `parallelStream()` ne doit pas être utilisé automatiquement dans l'espoir d'améliorer les performances.

Son intérêt dépend notamment :

- du volume de données ;
- du coût des opérations ;
- du nombre de processeurs ;
- des dépendances entre opérations ;
- du contexte d'exécution.

---

# 14. Transformation

## `replaceAll(UnaryOperator<E> operator)`

Modifie chaque élément selon une fonction.

```java
developers.replaceAll(String::toUpperCase);
```

---

## `sort(Comparator<? super E> comparator)`

Trie la liste.

```java
developers.sort(String::compareTo);
```

Ordre inverse :

```java
developers.sort(Comparator.reverseOrder());
```

---

# 15. Sous-liste

## `subList(int fromIndex, int toIndex)`

Retourne une vue d'une portion de la liste.

```java
List<String> subset =
        developers.subList(1, 3);
```

L'index de départ est inclus.

L'index de fin est exclu.

```text
[fromIndex, toIndex[
```

Il faut également comprendre que `subList()` retourne une vue liée à la liste originale et non nécessairement une copie indépendante.

---

# 16. Conversion vers un tableau

## `toArray()`

```java
Object[] array = developers.toArray();
```

---

## `toArray(T[] array)`

```java
String[] array =
        developers.toArray(new String[0]);
```

---

## `toArray(IntFunction<T[]> generator)`

Une autre variante héritée de `Collection` permet notamment :

```java
String[] array =
        developers.toArray(String[]::new);
```

---

# 17. Spliterator

## `spliterator()`

Retourne un `Spliterator`.

```java
Spliterator<String> spliterator =
        developers.spliterator();
```

`Spliterator` est notamment utilisé pour permettre le découpage d'un ensemble d'éléments, en particulier dans le contexte des Streams et du traitement parallèle.

Ce sujet sera étudié séparément.

---

# 18. Comparaison des listes

## `equals(Object object)`

Deux listes sont considérées égales lorsque leurs éléments correspondants sont égaux et apparaissent dans le même ordre.

Par exemple :

```java
List<String> first =
        List.of("Java", "Spring");

List<String> second =
        List.of("Java", "Spring");

System.out.println(first.equals(second));
```

Résultat :

```text
true
```

Alors que :

```java
List<String> second =
        List.of("Spring", "Java");
```

ne représente pas la même liste du point de vue du contrat `List`.

---

# 19. `hashCode()`

`List` définit également un contrat spécifique pour le calcul du hash code.

Il doit être cohérent avec `equals()` :

```text
a.equals(b) == true
```

implique :

```text
a.hashCode() == b.hashCode()
```

---

# 20. Création de listes avec `List.of()`

Java fournit plusieurs méthodes statiques `List.of(...)`.

```java
List<String> technologies =
        List.of("Java", "Spring Boot", "React");
```

La liste obtenue n'est pas modifiable.

Par exemple :

```java
technologies.add("Angular");
```

provoquera une :

```text
UnsupportedOperationException
```

---

# 21. `List.copyOf()`

Permet de créer une liste non modifiable à partir d'une collection.

```java
List<String> copy =
        List.copyOf(developers);
```

Il faut distinguer :

```text
copie
```

et :

```text
vue
```

Ces deux notions seront étudiées plus précisément dans les exemples.

---

# 22. Méthodes principales à connaître

| Catégorie       | Méthodes                                                          |
| --------------- | ----------------------------------------------------------------- |
| Taille          | `size()`, `isEmpty()`                                             |
| Ajout           | `add()`, `addAll()`, `addFirst()`, `addLast()`                    |
| Lecture         | `get()`, `getFirst()`, `getLast()`                                |
| Modification    | `set()`, `replaceAll()`                                           |
| Recherche       | `contains()`, `containsAll()`, `indexOf()`, `lastIndexOf()`       |
| Suppression     | `remove()`, `removeAll()`, `removeIf()`, `retainAll()`, `clear()` |
| Extrémités      | `removeFirst()`, `removeLast()`                                   |
| Parcours        | `iterator()`, `listIterator()`, `forEach()`                       |
| Stream          | `stream()`, `parallelStream()`                                    |
| Tri             | `sort()`                                                          |
| Vue             | `subList()`, `reversed()`                                         |
| Conversion      | `toArray()`                                                       |
| Utilitaires     | `spliterator()`                                                   |
| Comparaison     | `equals()`, `hashCode()`                                          |
| Factory methods | `List.of()`, `List.copyOf()`                                      |

---

# 23. Ce que List ne définit pas

L'interface `List` définit principalement un **contrat**.

Elle ne définit pas comment les éléments doivent être stockés physiquement.

Par exemple :

```text
ArrayList
    → tableau dynamique

LinkedList
    → liste doublement chaînée
```

Ces différences d'implémentation ont des conséquences importantes sur :

- les performances ;
- la consommation mémoire ;
- l'accès par index ;
- les insertions ;
- les suppressions ;
- le comportement du cache CPU.

Ces aspects seront étudiés dans les documentations spécifiques aux implémentations.

---

# 24. Prochaines documentations

```text
list/
├── list-interface.md
│
├── arraylist/
│   ├── README.md
│   ├── creation-capacity.md
│   ├── operations.md
│   ├── performance.md
│   └── pitfalls.md
│
└── linkedlist/
    ├── README.md
    ├── operations.md
    ├── performance.md
    └── pitfalls.md
```

L'objectif est de distinguer clairement :

```text
List
    = contrat

ArrayList
    = implémentation basée sur un tableau dynamique

LinkedList
    = implémentation basée sur une liste doublement chaînée
```

Cette distinction sera essentielle pour comprendre correctement le Java Collections Framework.
