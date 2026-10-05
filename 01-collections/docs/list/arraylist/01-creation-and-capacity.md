# ArrayList — Création et gestion de la capacité

## 1. Objectif

Cette section présente la création d'une `ArrayList` et explique une notion
fondamentale de son fonctionnement : la différence entre **taille** (`size`)
et **capacité** (`capacity`).

Nous allons étudier :

- le fonctionnement général d'une `ArrayList` ;
- les différents constructeurs de `ArrayList` ;
- la différence entre taille et capacité ;
- les méthodes `size()` et `isEmpty()` ;
- la méthode `ensureCapacity()` ;
- la méthode `trimToSize()` ;
- la croissance automatique de la capacité ;
- l'impact du dimensionnement sur les performances ;
- les bonnes pratiques liées à la capacité d'une `ArrayList`.

---

## 2. Qu'est-ce qu'une `ArrayList` ?

`ArrayList<E>` est une implémentation de l'interface `List<E>` du
Java Collections Framework.

Elle utilise un **tableau redimensionnable** pour stocker ses éléments.

Exemple :

```java
import java.util.ArrayList;
import java.util.List;

List<String> technologies = new ArrayList<>();
```

Contrairement à un tableau Java classique :

```java
String[] technologies = new String[3];
```

dont la taille est fixée lors de sa création, une `ArrayList` peut évoluer
dynamiquement lorsque de nouveaux éléments sont ajoutés ou supprimés.

Exemple :

```java
List<String> technologies = new ArrayList<>();

technologies.add("Java");
technologies.add("Spring Boot");
technologies.add("React");
```

La liste contient maintenant :

```text
[Java, Spring Boot, React]
```

La gestion du stockage interne est assurée automatiquement par `ArrayList`.

---

## 3. Taille et capacité

Pour comprendre le fonctionnement d'une `ArrayList`, il faut distinguer deux
notions :

- la **taille** ;
- la **capacité**.

Elles représentent deux choses différentes.

### 3.1 Taille — `size`

La taille correspond au **nombre réel d'éléments contenus dans la liste**.

Elle est accessible grâce à :

```java
size()
```

Exemple :

```java
List<String> technologies = new ArrayList<>();

technologies.add("Java");
technologies.add("Spring Boot");
technologies.add("React");

System.out.println(technologies.size());
```

Résultat :

```text
3
```

La liste contient réellement trois éléments.

---

### 3.2 Capacité — `capacity`

La capacité correspond à la taille du tableau utilisé en interne par
`ArrayList` pour stocker ses éléments.

Conceptuellement :

```text
ArrayList

size = 3
capacity = 6

┌────────┬─────────────┬───────┬──────┬──────┬──────┐
│ Java   │ Spring Boot │ React │      │      │      │
└────────┴─────────────┴───────┴──────┴──────┴──────┘
    0          1           2
```

Dans cet exemple conceptuel :

```text
size = 3
```

car trois éléments sont réellement présents.

La capacité serait :

```text
capacity = 6
```

car le tableau interne disposerait de six emplacements.

> Le schéma ci-dessus est uniquement une représentation pédagogique.
> Il ne permet pas de déduire la capacité réelle d'une instance donnée.

La capacité d'une `ArrayList` est toujours au moins aussi grande que sa taille.

```text
capacity >= size
```

---

## 4. La capacité n'est pas directement accessible

La taille peut être obtenue directement :

```java
list.size();
```

En revanche, `ArrayList` ne fournit pas de méthode publique comme :

```java
list.capacity();
```

ou :

```java
list.getCapacity();
```

La capacité fait partie du mécanisme interne de stockage de l'`ArrayList`.

L'API publique fournit cependant deux méthodes permettant d'agir sur cette
capacité :

```java
ensureCapacity(...)
```

et :

```java
trimToSize()
```

---

# 5. Créer une `ArrayList` vide

Le constructeur le plus simple est :

```java
new ArrayList<>()
```

Exemple :

```java
ArrayList<String> technologies = new ArrayList<>();
```

ou, en privilégiant l'interface :

```java
List<String> technologies = new ArrayList<>();
```

La liste est vide.

```java
System.out.println(technologies.size());
```

Résultat :

```text
0
```

Et :

```java
System.out.println(technologies.isEmpty());
```

Résultat :

```text
true
```

Selon le contrat public de `ArrayList` en Java 21, le constructeur sans
argument crée une liste vide avec une capacité initiale de 10.

Il est cependant important de ne pas faire dépendre le code applicatif de la
manière exacte dont le tableau interne est alloué ou matérialisé.

Ce qui importe du point de vue de l'API est que :

```java
new ArrayList<>()
```

produit une liste vide et que sa capacité pourra évoluer automatiquement
lorsque des éléments seront ajoutés.

---

# 6. `size()`

La méthode :

```java
size()
```

retourne le nombre d'éléments actuellement présents dans la liste.

Exemple :

```java
ArrayList<String> technologies = new ArrayList<>();

System.out.println(technologies.size());
```

Résultat :

```text
0
```

Après quelques ajouts :

```java
technologies.add("Java");
technologies.add("Spring Boot");
technologies.add("React");

System.out.println(technologies.size());
```

Résultat :

```text
3
```

`size()` retourne donc le nombre d'éléments et non la capacité du tableau
interne.

---

# 7. `isEmpty()`

La méthode :

```java
isEmpty()
```

permet de déterminer si une liste ne contient aucun élément.

Exemple :

```java
ArrayList<String> technologies = new ArrayList<>();

System.out.println(technologies.isEmpty());
```

Résultat :

```text
true
```

Après l'ajout d'un élément :

```java
technologies.add("Java");

System.out.println(technologies.isEmpty());
```

Résultat :

```text
false
```

Conceptuellement :

```text
isEmpty() == true
```

équivaut à une liste ne contenant aucun élément.

Il est généralement préférable d'utiliser :

```java
list.isEmpty()
```

plutôt que :

```java
list.size() == 0
```

car l'intention du code est plus explicite.

---

# 8. Créer une `ArrayList` avec une capacité initiale

`ArrayList` possède un constructeur permettant de préciser la capacité
initiale :

```java
new ArrayList<>(initialCapacity)
```

Exemple :

```java
ArrayList<String> technologies = new ArrayList<>(100);
```

Cela signifie que l'on crée une liste vide prévue pour disposer d'une
capacité initiale de 100 éléments.

Attention :

```java
new ArrayList<>(100)
```

ne crée pas une liste contenant 100 éléments.

Exemple :

```java
ArrayList<String> technologies = new ArrayList<>(100);

System.out.println(technologies.size());
```

Résultat :

```text
0
```

Donc :

```text
initialCapacity = 100
size            = 0
```

Les deux notions sont indépendantes.

---

## 8.1 Pourquoi préciser une capacité initiale ?

Lorsqu'on sait à l'avance qu'une liste contiendra un grand nombre d'éléments,
il peut être intéressant de lui fournir une capacité initiale adaptée.

Exemple :

```java
int expectedUsers = 10_000;

ArrayList<User> users = new ArrayList<>(expectedUsers);
```

Cela peut éviter une partie des redimensionnements progressifs du tableau
interne pendant l'ajout des éléments.

Sans capacité adaptée, une liste peut évoluer conceptuellement ainsi :

```text
capacité insuffisante
        │
        ▼
création d'un stockage plus grand
        │
        ▼
copie des références existantes
        │
        ▼
nouveau stockage interne
```

Cette opération peut se produire plusieurs fois pendant la croissance de la
liste.

Si le nombre approximatif d'éléments est connu, une capacité initiale adaptée
peut donc réduire les réallocations intermédiaires.

---

## 8.2 Capacité initiale négative

La capacité initiale ne peut pas être négative.

Ce code :

```java
new ArrayList<String>(-1);
```

provoque :

```text
IllegalArgumentException
```

Exemple :

```java
try {
    ArrayList<String> technologies = new ArrayList<>(-1);
} catch (IllegalArgumentException exception) {
    System.out.println("Invalid initial capacity");
}
```

---

# 9. Créer une `ArrayList` depuis une collection

Le troisième constructeur permet de créer une `ArrayList` contenant les
éléments d'une collection existante :

```java
new ArrayList<>(collection)
```

Exemple :

```java
List<String> backendTechnologies =
        List.of("Java", "Spring Boot", "PostgreSQL");

ArrayList<String> technologies =
        new ArrayList<>(backendTechnologies);
```

La nouvelle liste contient :

```text
[Java, Spring Boot, PostgreSQL]
```

Les éléments sont ajoutés dans l'ordre fourni par l'itérateur de la collection
source.

La taille est donc :

```java
System.out.println(technologies.size());
```

Résultat :

```text
3
```

---

## 9.1 La nouvelle liste est indépendante de la collection source

Considérons :

```java
ArrayList<String> source = new ArrayList<>();

source.add("Java");
source.add("Spring Boot");

ArrayList<String> copy = new ArrayList<>(source);
```

Nous avons maintenant deux listes distinctes :

```text
source = [Java, Spring Boot]
copy   = [Java, Spring Boot]
```

Si nous modifions structurellement `source` :

```java
source.add("React");
```

nous obtenons :

```text
source = [Java, Spring Boot, React]
copy   = [Java, Spring Boot]
```

Ajouter un élément dans la première liste n'ajoute donc pas automatiquement
cet élément dans la seconde.

Les deux structures de liste sont indépendantes.

---

# 10. Attention : il s'agit d'une copie superficielle

Même si les deux listes sont différentes, les objets qu'elles contiennent ne
sont pas automatiquement clonés.

Considérons une classe :

```java
class Developer {

    private String name;

    public Developer(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

Créons un objet :

```java
Developer developer = new Developer("Alice");
```

Puis une liste :

```java
ArrayList<Developer> source = new ArrayList<>();

source.add(developer);
```

Créons ensuite une autre `ArrayList` :

```java
ArrayList<Developer> copy = new ArrayList<>(source);
```

Conceptuellement :

```text
source ─────┐
            │
            ├────► Developer("Alice")
            │
copy ───────┘
```

Les deux listes contiennent une référence vers le même objet `Developer`.

Si nous faisons :

```java
developer.setName("Bob");
```

l'objet visible depuis les deux listes porte maintenant le nom :

```text
Bob
```

Le constructeur :

```java
new ArrayList<>(collection)
```

réalise donc une copie des éléments de la collection au sens des
**références**, mais ne crée pas une copie profonde des objets.

On parle de :

```text
shallow copy
```

ou :

```text
copie superficielle
```

---

# 11. Collection source `null`

Le constructeur :

```java
new ArrayList<>(collection)
```

n'accepte pas une collection `null`.

Par exemple :

```java
List<String> source = null;

ArrayList<String> technologies =
        new ArrayList<>(source);
```

provoque :

```text
NullPointerException
```

Il faut cependant distinguer :

```text
collection == null
```

et :

```text
collection contenant un élément null
```

`ArrayList` permet elle-même de stocker des éléments `null`.

Par exemple :

```java
ArrayList<String> technologies = new ArrayList<>();

technologies.add(null);
```

est autorisé.

L'étude détaillée des ajouts sera faite dans la section suivante.

---

# 12. Croissance automatique de la capacité

L'un des avantages d'une `ArrayList` est que le développeur n'a généralement
pas besoin de gérer manuellement sa capacité.

Exemple :

```java
ArrayList<Integer> numbers = new ArrayList<>();

for (int i = 0; i < 100_000; i++) {
    numbers.add(i);
}
```

La liste adapte automatiquement sa capacité lorsque le stockage disponible
devient insuffisant.

Conceptuellement :

```text
Ajout d'éléments
      │
      ▼
Capacité suffisante ?
      │
 ┌────┴────┐
 │         │
Oui       Non
 │         │
 ▼         ▼
Ajout    Agrandissement
          du stockage
             │
             ▼
           Ajout
```

La stratégie exacte utilisée pour augmenter la capacité n'est pas spécifiée
par le contrat public de `ArrayList`.

Il ne faut donc pas écrire de code dépendant d'une formule particulière de
croissance.

Par exemple, il ne faut pas considérer comme une garantie de l'API une règle
du type :

```text
nouvelle capacité = ancienne capacité × X
```

Même si une implémentation particulière de Java utilise une stratégie
spécifique, celle-ci reste un détail d'implémentation.

---

# 13. `ensureCapacity(int minCapacity)`

`ArrayList` fournit la méthode :

```java
ensureCapacity(int minCapacity)
```

Elle permet de demander que l'`ArrayList` puisse contenir **au moins** le
nombre d'éléments indiqué avant qu'une augmentation supplémentaire de capacité
ne soit nécessaire.

Exemple :

```java
ArrayList<String> technologies = new ArrayList<>();

technologies.ensureCapacity(10_000);
```

Cela peut être utile lorsque la liste existe déjà et que l'on sait qu'un grand
nombre d'éléments vont prochainement être ajoutés.

Exemple :

```java
ArrayList<Order> orders = new ArrayList<>();

int expectedOrders = 10_000;

orders.ensureCapacity(expectedOrders);
```

L'objectif est de réduire les éventuelles réallocations successives pendant
les ajouts.

---

## 13.1 `ensureCapacity()` ne modifie pas la taille

C'est un point essentiel.

```java
ArrayList<String> technologies = new ArrayList<>();

technologies.ensureCapacity(10_000);

System.out.println(technologies.size());
```

Résultat :

```text
0
```

La méthode agit sur la capacité, pas sur le nombre d'éléments.

Donc :

```text
ensureCapacity(10_000)
```

ne signifie pas :

```text
size = 10_000
```

mais conceptuellement :

```text
capacité suffisante pour accueillir au moins 10 000 éléments
```

La liste reste vide tant qu'aucun élément n'a été ajouté.

---

## 13.2 Quand utiliser `ensureCapacity()` ?

Cette méthode peut être intéressante lorsque :

- une `ArrayList` existe déjà ;
- le nombre d'éléments à ajouter devient connu ;
- un grand nombre d'éléments doivent être ajoutés ;
- on souhaite limiter les redimensionnements successifs.

Exemple :

```java
ArrayList<Product> products = new ArrayList<>();

int numberOfProductsToLoad = 50_000;

products.ensureCapacity(numberOfProductsToLoad);
```

Pour de petites listes ou lorsque le volume est inconnu, il n'est généralement
pas nécessaire d'utiliser explicitement `ensureCapacity()`.

---

# 14. `trimToSize()`

Une `ArrayList` peut disposer d'une capacité supérieure à sa taille actuelle.

Exemple conceptuel :

```text
size = 3
capacity = 100

┌──────┬─────────────┬───────┬───────────────────────┐
│ Java │ Spring Boot │ React │ espace non utilisé... │
└──────┴─────────────┴───────┴───────────────────────┘
```

La méthode :

```java
trimToSize()
```

permet de réduire la capacité de l'`ArrayList` à sa taille actuelle.

Exemple :

```java
ArrayList<String> technologies =
        new ArrayList<>(100);

technologies.add("Java");
technologies.add("Spring Boot");
technologies.add("React");

technologies.trimToSize();
```

Conceptuellement :

```text
AVANT

size = 3
capacity = 100


        trimToSize()

             │
             ▼


APRÈS

size = 3
capacity = 3
```

La méthode peut être utilisée pour réduire le stockage inutilisé lorsque la
liste a atteint une taille relativement stable.

---

## 14.1 `trimToSize()` ne supprime pas les éléments

Considérons :

```java
ArrayList<String> technologies =
        new ArrayList<>(100);

technologies.add("Java");
technologies.add("Spring Boot");
technologies.add("React");

technologies.trimToSize();
```

Après l'appel :

```java
System.out.println(technologies.size());
```

retourne toujours :

```text
3
```

et la liste contient toujours :

```text
[Java, Spring Boot, React]
```

`trimToSize()` agit sur le stockage interne, pas sur le contenu logique de la
liste.

---

# 15. Faut-il appeler `trimToSize()` systématiquement ?

Non.

Imaginons :

```java
ArrayList<String> technologies =
        new ArrayList<>(1000);
```

Après avoir ajouté trois éléments :

```java
technologies.add("Java");
technologies.add("Spring Boot");
technologies.add("React");
```

nous appelons :

```java
technologies.trimToSize();
```

Si de nombreux éléments sont ajoutés ensuite, la liste devra de nouveau
augmenter sa capacité.

Conceptuellement :

```text
grande capacité
     │
     ▼
trimToSize()
     │
     ▼
capacité réduite
     │
     ▼
nouveaux ajouts importants
     │
     ▼
nouvel agrandissement
```

Une utilisation excessive de `trimToSize()` peut donc être contre-productive.

Elle est principalement pertinente lorsque :

- la liste a précédemment eu une grande capacité ;
- sa taille finale est maintenant relativement stable ;
- la liste restera longtemps en mémoire ;
- l'économie de mémoire est réellement utile ;
- peu de nouveaux éléments devraient être ajoutés.

---

# 16. Capacité initiale ou `ensureCapacity()` ?

Les deux mécanismes répondent à des situations légèrement différentes.

## Capacité connue lors de la création

Si le nombre approximatif d'éléments est déjà connu :

```java
int expectedUsers = 10_000;

ArrayList<User> users =
        new ArrayList<>(expectedUsers);
```

Le constructeur avec capacité initiale est naturel.

---

## Capacité connue plus tard

Si la liste existe déjà :

```java
ArrayList<User> users = new ArrayList<>();
```

et que l'on découvre ensuite le nombre d'éléments à charger :

```java
int expectedUsers = 10_000;

users.ensureCapacity(expectedUsers);
```

`ensureCapacity()` permet d'adapter la capacité avant les ajouts massifs.

---

# 17. Complexité de l'ajout en fin de liste

Même si l'étude complète de `add()` sera faite dans la section suivante,
la capacité permet déjà de comprendre une caractéristique importante
d'`ArrayList`.

L'ajout d'un élément à la fin d'une `ArrayList` est considéré comme :

```text
O(1) amorti
```

Cela ne signifie pas que chaque appel individuel à `add()` a exactement le
même coût.

Lorsque la capacité est suffisante :

```text
[Java][Spring][    ][    ]
                │
                ▼
             add("React")
                │
                ▼
[Java][Spring][React][   ]
```

l'ajout est peu coûteux.

En revanche, lorsqu'un agrandissement est nécessaire, l'opération peut
nécessiter la création d'un stockage plus grand et la copie des références
existantes.

Conceptuellement :

```text
ancien stockage
       │
       ▼
capacité insuffisante
       │
       ▼
nouveau stockage plus grand
       │
       ▼
copie des références
       │
       ▼
ajout du nouvel élément
```

Sur une longue série d'ajouts, le coût moyen reste constant amorti.

---

# 18. Pourquoi ne pas toujours utiliser une très grande capacité ?

On pourrait être tenté d'écrire :

```java
ArrayList<User> users =
        new ArrayList<>(10_000_000);
```

simplement pour éviter tout redimensionnement.

Ce n'est pas une bonne stratégie sans justification.

Une capacité très supérieure aux besoins réels peut conduire à réserver plus
de stockage que nécessaire.

Il faut donc trouver un équilibre entre :

```text
trop petite capacité
        ↓
redimensionnements potentiels
```

et :

```text
capacité beaucoup trop grande
        ↓
stockage potentiellement gaspillé
```

La capacité initiale doit être utilisée lorsqu'une estimation raisonnable du
nombre d'éléments existe.

---

# 19. Type `List` ou `ArrayList` ?

Dans le code applicatif, on privilégie généralement le contrat :

```java
List<String> technologies = new ArrayList<>();
```

plutôt que :

```java
ArrayList<String> technologies = new ArrayList<>();
```

Cela réduit le couplage avec l'implémentation concrète.

Cependant, certaines méthodes étudiées dans cette section sont spécifiques à
`ArrayList`.

Par exemple :

```java
ensureCapacity()
```

et :

```java
trimToSize()
```

ne font pas partie du contrat général de `List`.

Dans ce cas, il peut être nécessaire de manipuler explicitement le type :

```java
ArrayList<String> technologies = new ArrayList<>();

technologies.ensureCapacity(1000);
```

La règle n'est donc pas :

```text
toujours utiliser List
```

mais plutôt :

```text
dépendre de l'abstraction lorsque l'API spécifique
de l'implémentation n'est pas nécessaire
```

---

# 20. Bonnes pratiques

## Privilégier le constructeur par défaut dans les cas ordinaires

Pour la majorité des listes :

```java
List<String> technologies = new ArrayList<>();
```

est parfaitement suffisant.

La croissance de la capacité est gérée automatiquement.

---

## Fournir une capacité lorsque le volume est réellement prévisible

Si l'on sait qu'une liste doit accueillir environ 100 000 éléments :

```java
ArrayList<String> values =
        new ArrayList<>(100_000);
```

peut être pertinent.

---

## Ne pas confondre capacité et taille

Ceci :

```java
new ArrayList<>(100);
```

ne produit pas :

```text
size = 100
```

mais :

```text
size = 0
```

avec une capacité initiale de 100.

---

## Ne pas chercher à lire la capacité par réflexion

`ArrayList` ne fournit pas d'API publique pour lire directement sa capacité.

Pour du code applicatif et des tests standards, il vaut mieux respecter cette
abstraction plutôt que d'accéder aux champs internes de l'implémentation.

---

## Ne pas dépendre de la formule de croissance interne

La politique exacte de croissance n'est pas spécifiée par le contrat public.

Le code ne doit donc pas supposer une formule particulière.

---

## Ne pas utiliser `trimToSize()` automatiquement

Utiliser cette méthode uniquement lorsqu'une réduction du stockage apporte
réellement un bénéfice.

---

# 21. Résumé des constructeurs

| Constructeur                       | Description                                                      |
| ---------------------------------- | ---------------------------------------------------------------- |
| `new ArrayList<>()`                | Crée une liste vide                                              |
| `new ArrayList<>(initialCapacity)` | Crée une liste vide avec la capacité initiale spécifiée          |
| `new ArrayList<>(collection)`      | Crée une liste contenant les éléments d'une collection existante |

---

# 22. Résumé des méthodes étudiées

| Méthode             | Rôle                                                                |
| ------------------- | ------------------------------------------------------------------- |
| `size()`            | Retourne le nombre d'éléments présents                              |
| `isEmpty()`         | Indique si la liste est vide                                        |
| `ensureCapacity(n)` | Demande une capacité suffisante pour contenir au moins `n` éléments |
| `trimToSize()`      | Réduit la capacité à la taille actuelle                             |

---

# 23. Taille vs capacité — résumé

```text
TAILLE
│
├── nombre d'éléments présents
├── accessible avec size()
└── évolue avec l'ajout et la suppression d'éléments


CAPACITÉ
│
├── taille du stockage interne
├── toujours >= size
├── augmente automatiquement si nécessaire
├── peut être influencée par ensureCapacity()
├── peut être réduite avec trimToSize()
└── n'est pas directement exposée par une méthode capacity()
```

---

# 24. À retenir

`ArrayList` repose sur un tableau redimensionnable.

La **taille** représente le nombre d'éléments actuellement contenus dans la
liste :

```java
list.size();
```

La **capacité** correspond à la taille du stockage interne disponible pour les
éléments.

Elle n'est pas directement accessible depuis l'API publique.

Dans la majorité des situations :

```java
new ArrayList<>()
```

est suffisant.

Lorsqu'un grand volume d'éléments est connu dès la création :

```java
new ArrayList<>(expectedSize)
```

peut réduire les redimensionnements intermédiaires.

Lorsqu'une liste existe déjà :

```java
list.ensureCapacity(expectedSize);
```

peut permettre d'anticiper une croissance importante.

Enfin :

```java
list.trimToSize();
```

permet de réduire la capacité à la taille actuelle lorsque cela apporte un
véritable bénéfice mémoire.

La gestion explicite de la capacité est donc principalement une optimisation.
Elle ne doit être utilisée que lorsqu'elle répond à un besoin concret.
