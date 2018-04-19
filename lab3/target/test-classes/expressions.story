Scenario: Expression without saving
Given a new evaluator
When I evaluate string <input>
Then I should get result <result>

Examples:
|input                                                            |result                              |
|100.00 + 112.5                                                   |212.50                              |
|([2, -2, 1] + [1, 5, 2]) * [1, 2, 3]                             |18.00                               |
|3 + [1, 5, 2] * [1, 2, 3]                                        |20.00                               |
|[[1, 2], [2, 1]] + [[1, 2], [2, 1]]                              |[[2.00, 4.00], [4.00, 2.00]]        |
|det([[2, 4], [4, 2]])                                            |-12.00                              |
|[1, 5, 2] * [1, 2, 3] + det([[1, 2], [2, 1]] + [[1, 2], [2, 1]]) |5.00                                |

Scenario: Expression with saving variables
Given a new evaluator
When I evaluate string <input>
When I evaluate string <varname>
Then I should get result <result>

Examples:
|input                                                                |varname           |result                         |
|A = 100.00 + 112.5                                                   |A                 |212.50                         |
|B = ([2, -2, 1] + [1, 5, 2]) * [1, 2, 3]                             |B                 |18.00                          |
|C = 3 + [1, 5, 2] * [1, 2, 3]                                        |C                 |20.00                          |
|D = [[1, 2], [2, 1]] + [[1, 2], [2, 1]]                              |D                 |[[2.00, 4.00], [4.00, 2.00]]   |
|E = det([[2, 4], [4, 2]])                                            |E                 |-12.00                         |
|F = [1, 5, 2] * [1, 2, 3] + det([[1, 2], [2, 1]] + [[1, 2], [2, 1]]) |F                 |5.00                           |

Scenario: Invalid format
Given a new evaluator
When I evaluate string <input>
Then I should get an error message invalid input

Examples:
|input                                                            |
|100.00 + 112.5A                                                  |
|([2, -2, 1] + [1, 5, 2 ) * [1, 2, 3]                             |
|3 + [1, 5, 2) * [1, 2, 3]                                        |
|[[1, 2], 2, 1]] + [[1, 2], [2, 1]]                               |
|det([[[2, 4], [4, 2]])                                           |
|[1, 5, 2] * [1, 2, 3] + det([[1, 2 [2, 1]] + [[1, 2], [2, 1]])   |

Scenario: Querying saved values
Given a new evaluator
When I evaluate string <input1>
When I evaluate string <input2>
When I evaluate string <input3>
Then I should get result <result>

Examples:
|input1                   |input2                |input3                |result                       |
|A = 100.00               |B = -25               |A + B                 |75.00                        |
|A = [2, -2, 1]           |B = [1, 5, 2]         |(A + B) * [1, 2, 3]   |18.00                        |
|A = [[1, 2], [2, 1]]     |B = [[1, 2], [2, 1]]  |A + B                 |[[2.00, 4.00], [4.00, 2.00]] |

Scenario: Querying saved values and saving new variable
Given a new evaluator
When I evaluate string <input1>
When I evaluate string <input2>
When I evaluate string <input3>
When I evaluate string <input4>
Then I should get result <result>

Examples:
|input1               |input2               |input3                  |input4 |result                       |
|A = 100.00           |B = -25              |C = A + B               |C      |75.00                        |
|A = [2, -2, 1]       |B = [1, 5, 2]        |C = (A + B) * [1, 2, 3] |C      |18.00                        |
|A = [[1, 2], [2, 1]] |B = [[1, 2], [2, 1]] |C = A + B               |C      |[[2.00, 4.00], [4.00, 2.00]] |

Scenario: Querying a variable that is not saved
Given a new evaluator
When I evaluate string <input>
Then I should get an error message no such variable

Examples:
|input  |
|A + B  |
|det(A) |
|C * W  |