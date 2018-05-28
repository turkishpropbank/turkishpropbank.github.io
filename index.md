---
layout: page
title: Turkish PropBank
published: true
---
<script type="text/javascript" src="{{ site.baseurl }}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="{{ site.baseurl }}/js/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="{{ site.baseurl }}/js/verb-autocomplete.js"></script>

Turkish Proposition Bank is the first semantically annotated corpus built for Turkish language. It is constructed upon [IMST](http://tools.nlp.itu.edu.tr/Datasets) and later alinged with [IMST Universal Dependencies (UD)](http://universaldependencies.org/#tr). It contains sense and semantic argument information of each predicate similar to

![_config.yml]({{ site.baseurl }}/images/example_scaledDown.png)

_giy.01_ means the "1st" sense of predicate lemma _giy (wear)_ and labels A-A, A0 and A1 represents semantic arguments. Numbered arguments (A0-A4) of each predicate sense are registered in **frame files** and definitions of adjunct like arguments can be found in original [English PropBank project](http://propbank.github.io/).

### Download Resources

Frame files can be download from [this link](https://github.com/turkishpropbank/turkishpropbank.github.io/tree/resources/frames). 

~~Corpora constructed upon IMST and IMST-UD can be investigated from [project's corpus repo](https://github.com/turkishpropbank/turkishpropbank.github.io/tree/resources/corpus). Please check the LICENSE and README files before usage.~~

Annotated corpora are distributed from [İTÜ NLP Group - Datasets page](http://tools.nlp.itu.edu.tr/Datasets). The original version described in [1] is distributed under the tag *Original*. We also distribute the version compatible with the latest treebank release under the *Latest* tag. The same applies to Universal Dependency (UD) compatible annotations.

### Crowdsourcing

A part of this corpus has been annotated via crowd workers. To guide researchers we share our design files, screenshots, instructions and task results (aggregation files)[[Verb Sense Annotation]](https://github.com/turkishpropbank/turkishpropbank.github.io/tree/resources/crowdresults/vsa),
[[Semantic Role Annotation](https://github.com/turkishpropbank/turkishpropbank.github.io/tree/resources/crowdresults/sra)].   

### Browse Frames

<div id="searchfield">					
<form><input type="text" name="verb" class="biginput" id="autocomplete"></form>
</div>
<p id="outputcontent"> </p>

### Turkish Semantic Role Labeling

Source code based on [[mate-tools]](https://code.google.com/archive/p/mate-tools/) is available from [[here](https://github.com/turkishpropbank/turkishpropbank.github.io/tree/code)].  

### License

We distribute resources built in scope of this project under [Creative Commons BY-NC-SA 4.0 International license](https://creativecommons.org/licenses/by-nc-sa/4.0/).

### References

[1] Gözde Gül Şahin and Eşref Adalı. 2017. [Annotation of semantic roles for the Turkish Proposition Bank](https://link.springer.com/article/10.1007/s10579-017-9390-y). *Language Resources and Evaluation* (in press).

[2] Gözde Gül Şahin. 2016. [Verb Sense Annotation for Turkish PropBank via Crowdsourcing](https://link.springer.com/chapter/10.1007/978-3-319-75477-2_35). In *Proceedings of 17th International Conference on Computational Linguistics and Intelligent Text Processing (CICLing 2016)*, Konya, Turkey, April 3-9, 2016, Revised Selected Papers, Part I. pages 496–506.

[3] Gözde Gül Şahin. 2016. Framing of Verbs for Turkish PropBank. In *Proceedings of 1st International Conference on Turkic Computational Linguistics, TurCLing*, Konya, Turkey, April 9, 2016

[4] Gözde Gül Şahin and Eşref Adalı. 2014. [Using morphosemantic information in construction of a pilot lexical semantic resource for Turkish](http://www.aclweb.org/anthology/W14-5807). In *Proceedings of Workshop on Lexical and Grammatical resources for Language Processing*, Dublin, Ireland.
