# turkishpropbank.github.io
Source code for Turkish SRL baseline. 

**Settings for Learn.java** 

tur (language)

tr-ud-train_dev.conllu (path for training data)

baseline.mdl (model name to save)

-reranker (if you want reranking)

-ud (should be written if you work with universal dependencies)

**Settings for Parse.java**

tur (language)

tr-ud-test.conllu (test file)

baseline.mdl (model to use)

-reranker (use reranking)

-nopi (skip predicate identification step as in SRL-only shared task)

-ud (working with universal dependencies)

prediction.Out (named of the output file)
