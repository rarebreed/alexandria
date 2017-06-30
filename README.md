# alexandria 

In a nutshell, alexandria is a database for your test code.  By attaching metadata to your test code about what features it is
testing and any other useful data, you can then run queries about your tests.  The source code will always be the source of 
truth of this metadata and it will be imported into alexandria.  Alexandria is just a mirror into your test ware definitions, 
and not dictatorial like some ALM tools that want you to write up requirements and test case definitions in the product first, 
and then somehow link your automation test code to those definitions. 

# Rationale

When you write tests for a product, there is a need to contain metadata about your tests.  At an enterprise level, just javing 
a bunch of unit tests or even automated system tests is not enough.  You should be able to point to definitions of requirements
and the test case definitions that verify those requirements.  Customers quite often want to see a high level description of 
these, and not just be shown some source code and be told "that _is_ our documentation".

But how do you capture this metadata?  For example, how do I define what the requirement is and how it is being tested?  One 
answer from the BDD world is to specify your requirements and testing in a feature file.  This file is structured with Given,
When, Then statements and helps the Product Owner, Developers and QA know exactly what is required of the feature and how to 
test it.  Some BDD frameworks then have tests written which search for these feature files on a local file system, and map the 
various Given, When, Then, And, But and other keywords to test functions.

But there's still a couple of problems.  First, not all test frameworks actually use these feature files.  Some are BDD-like, 
and they describe what a feature does and then state some assertion given some facts.  Secondly, even with a BDD file, you 
can't do useful things like query for tests based on features or given scenarios.

Imagine if you could link every test method to a feature.  Some test frameworks allow you to annotate or set tags in your test 
methods so that when the test runner runs, it will only run tests based on those annotations or flags.  In other words, it will
generate some subset of tests given those tags.  But wouldn't it be nice if you could do more complex logic on tags?  Or find 
relationships between tests?

This fundamentally is the goal of alexandria

## How to specify metadata

Ideally, the metadata should live in the source code itself.  The source code should be the canonical source of truth.  Some 
ALM products would like you to store your test case or requirements definitions in their ALM's database, and then somehow 
link those definitions to the actual test methods in your automation.  But there's a couple of problems with that.  Firstly,
it causes vendor lock in.  What if you dont like that ALM tool?  Well, then you're stuck.  Secondly, those ALM's are often 
walled off on the intranet and are unavailable for customers or collaborators to see.  Why should requirements and test case
definitions be essentially proprietary even if you have open source code?

But the problem with attaching metadata to your test code has its own problems.  What format should the metadata be?  And you 
can't query or do other data analysis on the metadata unless it is somehow read in and stored to a database.  So the idea is 
that you store metadata in a common format that can be imported into alexandria's database.

### Language implementations 

Each language has it's own specific way to handle metadata.  Java uses annotations, clojure uses metadata and python often uses
decorators for example. Ideally, there should be a language-friendly way of using it's natural way of using metadata that either 
creates common JSON, YAML or XML that can then be imported into alexandria.  For languages that don't have a particular way of 
specifying metadata (for example javascript until the decorators feature is enabled), plugins can be made to generate this.

The plugins that generate the common data format will be discussed separately.  What alexandria is interested in is the definition
file (ie the JSON, YAML or XML file) that holds the metadata.  In other words, alexandria is really interested in the schema.

## Definition files

As noted earlier, the metadata specified in the test code will generate some definition file and alexandria can consume this 
definition file so that it can be put into the database.
