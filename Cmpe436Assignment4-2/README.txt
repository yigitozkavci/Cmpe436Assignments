A iff B
= A if B and B if A

Part 1:
if s -> t, then (s.v[s.p] <= t.v[s.p]) and (s.v[t.p] < t.v[t.p])

Since s -> t, we know that s.v < t.v by the proof in page 24 of lecture52.pdf.
s.v < t.v already implies (s.v[s.p] <= t.v[s.p]) and (s.v[t.p] < t.v[t.p])

Part 2:
if (s.v[s.p] <= t.v[s.p]) and (s.v[t.p] < t.v[t.p]), then s -> t
Case 1:
  s.p = t.p =>
    This case's assumption makes (s.v[s.p] <= t.v[s.p]) redundant
    and (s.v[t.p] < t.v[t.p]) implies s happened before t, then s -> t.
Case 2:
  s.p != t.p =>
    (s.v[s.p] <= t.v[s.p]) and (s.v[t.p] < t.v[t.p])
    indicates a message that was sent from s to t.
    When a message from process a to process b occurs, b.v[a.p] is set to max(a.v[a.p], b.v[a.p]), and here,
    First part says that when message was sent, this update was turnt in favor of t's knowledge of s.p.
    And the second part indicates that s.v[t.p] <= t.v[t.p] before t incremented its clock by 1.
    These evidences proove that a message was sent from s to t, hence s -> t.
