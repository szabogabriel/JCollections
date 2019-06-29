# JCollections

This projects is an extension of the JDK by some data structures.

## DecisionTree

Decision tree creates a tree structures, where the keys of the map are strings.Every
character represents a new node in the tree structure, thus minimizing the amount of
key data stored. The children nodes are stored as a `HashMap<Character, DecisionTree>`
ensuring a lookup time of approximately `length * log(n)`.

Every node is capable of storing the target object type as a value.

Every default `Map` method is supported.

Removing an object results in removing the unused key part from the tree as well. For
example: `data.remove("abcd");` will remove the whole `"abcd"` key, as long as no 
substring of it is used as a key. Should `"ab"` used as a key, only the remaining substring
`"cd"` would be removed as unused. Removing an object makes a lookup of unused keys through
the whole structure, thus it ends gradually slows down with the size of the map.
