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

Frame files can be download from [this link](https://github.com/turkishpropbank/turkishpropbank.github.io/tree/resources/frames). Corpora constructed upon IMST and IMST-UD can be investigated from [project's corpus repo](https://github.com/turkishpropbank/turkishpropbank.github.io/tree/resources/corpus). Please check the LICENSE and README files before usage. 

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

### Cite

If you use this resource, please consider citing:

-Şahin, G.G. & Adalı, E. _Lang Resources & Evaluation Journal_ (2017).

-Şahin, G.G. Verb sense annotation for turkish propbank via crowdsourcing. In Proceedings of 17th international conference on intelligent text processing and computational linguistics. CICLING 2016.

-Şahin, G.G. (2015) Framing of verbs for turkish propbank. In Proceedings of 1st international conference on turkic computational linguistics, TurCLing

-Sahin, G.G. & Adalı, E. (2014). Using morphosemantic information in construction of a pilot lexical semantic resource for Turkish. In Workshop on lexical and grammatical resources for language processing (p. 46).

