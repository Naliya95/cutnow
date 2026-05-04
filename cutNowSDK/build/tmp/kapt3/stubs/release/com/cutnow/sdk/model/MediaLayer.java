package com.cutnow.sdk.model;

/**
 * Legacy/UI-level representation of an overlay layer.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000w\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0003\b\u00b8\u0001\b\u0086\b\u0018\u00002\u00020\u0001B\u0095\u0004\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u000e\u0012\u0010\b\u0002\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010 \u0012\b\b\u0002\u0010!\u001a\u00020\"\u0012\b\b\u0002\u0010#\u001a\u00020\u000e\u0012\b\b\u0002\u0010$\u001a\u00020\u000e\u0012\b\b\u0002\u0010%\u001a\u00020\u0007\u0012\b\b\u0002\u0010&\u001a\u00020\u0007\u0012\u0010\b\u0002\u0010\'\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010 \u0012\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)\u0012\b\b\u0002\u0010*\u001a\u00020\u000e\u0012\n\b\u0002\u0010+\u001a\u0004\u0018\u00010,\u0012\b\b\u0002\u0010-\u001a\u00020\u000e\u0012\b\b\u0002\u0010.\u001a\u00020/\u0012\b\b\u0002\u00100\u001a\u00020/\u0012\b\b\u0002\u00101\u001a\u00020/\u0012\b\b\u0002\u00102\u001a\u00020/\u0012\b\b\u0002\u00103\u001a\u00020\u0007\u0012\b\b\u0002\u00104\u001a\u00020\u0007\u0012\b\b\u0002\u00105\u001a\u00020\u0007\u0012\b\b\u0002\u00106\u001a\u00020\u0007\u0012\b\b\u0002\u00107\u001a\u00020\u0007\u0012\b\b\u0002\u00108\u001a\u00020\u0015\u0012\b\b\u0002\u00109\u001a\u00020\u000e\u0012\b\b\u0002\u0010:\u001a\u00020\u0015\u0012\b\b\u0002\u0010;\u001a\u00020\u0015\u0012\b\b\u0002\u0010<\u001a\u00020\u000e\u0012\b\b\u0002\u0010=\u001a\u00020\u000e\u0012\b\b\u0002\u0010>\u001a\u00020\u000e\u0012\b\b\u0002\u0010?\u001a\u00020@\u0012\u000e\b\u0002\u0010A\u001a\b\u0012\u0004\u0012\u00020C0B\u00a2\u0006\u0002\u0010DJ\n\u0010\u00c3\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00c4\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00c5\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00c6\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00c7\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00c8\u0001\u001a\u00020\u0015H\u00c6\u0003J\n\u0010\u00c9\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00ca\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00cb\u0001\u001a\u00020\u0019H\u00c6\u0003J\n\u0010\u00cc\u0001\u001a\u00020\u0019H\u00c6\u0003J\n\u0010\u00cd\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00ce\u0001\u001a\u00020\u0005H\u00c6\u0003J\n\u0010\u00cf\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00d0\u0001\u001a\u00020\u0019H\u00c6\u0003J\n\u0010\u00d1\u0001\u001a\u00020\u000eH\u00c6\u0003J\u0012\u0010\u00d2\u0001\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010 H\u00c6\u0003J\n\u0010\u00d3\u0001\u001a\u00020\"H\u00c6\u0003J\n\u0010\u00d4\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00d5\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00d6\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u00d7\u0001\u001a\u00020\u0007H\u00c6\u0003J\u0012\u0010\u00d8\u0001\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010 H\u00c6\u0003J\n\u0010\u00d9\u0001\u001a\u00020\u0007H\u00c6\u0003J\f\u0010\u00da\u0001\u001a\u0004\u0018\u00010)H\u00c6\u0003J\n\u0010\u00db\u0001\u001a\u00020\u000eH\u00c6\u0003J\f\u0010\u00dc\u0001\u001a\u0004\u0018\u00010,H\u00c6\u0003J\n\u0010\u00dd\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00de\u0001\u001a\u00020/H\u00c6\u0003J\n\u0010\u00df\u0001\u001a\u00020/H\u00c6\u0003J\n\u0010\u00e0\u0001\u001a\u00020/H\u00c6\u0003J\n\u0010\u00e1\u0001\u001a\u00020/H\u00c6\u0003J\n\u0010\u00e2\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u00e3\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u00e4\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u00e5\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u00e6\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u00e7\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u00e8\u0001\u001a\u00020\u0015H\u00c6\u0003J\n\u0010\u00e9\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00ea\u0001\u001a\u00020\u0015H\u00c6\u0003J\n\u0010\u00eb\u0001\u001a\u00020\u0015H\u00c6\u0003J\n\u0010\u00ec\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00ed\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00ee\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00ef\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u00f0\u0001\u001a\u00020@H\u00c6\u0003J\u0010\u0010\u00f1\u0001\u001a\b\u0012\u0004\u0012\u00020C0BH\u00c6\u0003J\n\u0010\u00f2\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u00f3\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u00f4\u0001\u001a\u00020\u000eH\u00c6\u0003J\n\u0010\u00f5\u0001\u001a\u00020\u000eH\u00c6\u0003J\u00a2\u0004\u0010\u00f6\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000e2\b\b\u0002\u0010\u0011\u001a\u00020\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u000e2\b\b\u0002\u0010\u0013\u001a\u00020\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u000e2\b\b\u0002\u0010\u0017\u001a\u00020\u000e2\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\b\b\u0002\u0010\u001b\u001a\u00020\u00032\b\b\u0002\u0010\u001c\u001a\u00020\u000e2\b\b\u0002\u0010\u001d\u001a\u00020\u00192\b\b\u0002\u0010\u001e\u001a\u00020\u000e2\u0010\b\u0002\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010 2\b\b\u0002\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020\u000e2\b\b\u0002\u0010$\u001a\u00020\u000e2\b\b\u0002\u0010%\u001a\u00020\u00072\b\b\u0002\u0010&\u001a\u00020\u00072\u0010\b\u0002\u0010\'\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010 2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)2\b\b\u0002\u0010*\u001a\u00020\u000e2\n\b\u0002\u0010+\u001a\u0004\u0018\u00010,2\b\b\u0002\u0010-\u001a\u00020\u000e2\b\b\u0002\u0010.\u001a\u00020/2\b\b\u0002\u00100\u001a\u00020/2\b\b\u0002\u00101\u001a\u00020/2\b\b\u0002\u00102\u001a\u00020/2\b\b\u0002\u00103\u001a\u00020\u00072\b\b\u0002\u00104\u001a\u00020\u00072\b\b\u0002\u00105\u001a\u00020\u00072\b\b\u0002\u00106\u001a\u00020\u00072\b\b\u0002\u00107\u001a\u00020\u00072\b\b\u0002\u00108\u001a\u00020\u00152\b\b\u0002\u00109\u001a\u00020\u000e2\b\b\u0002\u0010:\u001a\u00020\u00152\b\b\u0002\u0010;\u001a\u00020\u00152\b\b\u0002\u0010<\u001a\u00020\u000e2\b\b\u0002\u0010=\u001a\u00020\u000e2\b\b\u0002\u0010>\u001a\u00020\u000e2\b\b\u0002\u0010?\u001a\u00020@2\u000e\b\u0002\u0010A\u001a\b\u0012\u0004\u0012\u00020C0BH\u00c6\u0001J\u0015\u0010\u00f7\u0001\u001a\u00020\u00152\t\u0010\u00f8\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\n\u0010\u00f9\u0001\u001a\u00020\u0019H\u00d6\u0001J\n\u0010\u00fa\u0001\u001a\u00020\u0003H\u00d6\u0001R\u001a\u00103\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR\u001a\u00105\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bI\u0010F\"\u0004\bJ\u0010HR\u001a\u00104\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bK\u0010F\"\u0004\bL\u0010HR\u001a\u00100\u001a\u00020/X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\u001a\u00102\u001a\u00020/X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010N\"\u0004\bR\u0010PR\u001a\u00101\u001a\u00020/X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bS\u0010N\"\u0004\bT\u0010PR\u001a\u0010\u001a\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bU\u0010V\"\u0004\bW\u0010XR\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bY\u0010ZR\u0011\u0010[\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\\\u0010ZR\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b]\u0010F\"\u0004\b^\u0010HR\u001a\u0010*\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b_\u0010`\"\u0004\ba\u0010bR\u001c\u0010(\u001a\u0004\u0018\u00010)X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bc\u0010d\"\u0004\be\u0010fR\u0011\u0010g\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\bh\u0010FR\u001a\u0010-\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bi\u0010`\"\u0004\bj\u0010bR\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bk\u0010l\"\u0004\bm\u0010nR\u001a\u0010:\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bo\u0010p\"\u0004\bq\u0010rR\u001a\u0010;\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bs\u0010p\"\u0004\bt\u0010rR\u001a\u0010\u001b\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bu\u0010Z\"\u0004\bv\u0010wR\u001a\u0010\u001c\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bx\u0010`\"\u0004\by\u0010bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bz\u0010ZR\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010p\"\u0004\b{\u0010rR\u0017\u0010A\u001a\b\u0012\u0004\u0012\u00020C0B\u00a2\u0006\b\n\u0000\u001a\u0004\b|\u0010}R\u001a\u0010\u0017\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b~\u0010`\"\u0004\b\u007f\u0010bR\u001c\u0010\u0016\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0080\u0001\u0010`\"\u0005\b\u0081\u0001\u0010bR\u001c\u0010\r\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0082\u0001\u0010`\"\u0005\b\u0083\u0001\u0010bR\u001c\u0010\u000f\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0084\u0001\u0010`\"\u0005\b\u0085\u0001\u0010bR\u001c\u0010\u0013\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0086\u0001\u0010`\"\u0005\b\u0087\u0001\u0010bR\u001c\u0010\u0010\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0088\u0001\u0010`\"\u0005\b\u0089\u0001\u0010bR\u001c\u0010\u0011\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u008a\u0001\u0010`\"\u0005\b\u008b\u0001\u0010bR\u001c\u0010\u0012\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u008c\u0001\u0010`\"\u0005\b\u008d\u0001\u0010bR\u001c\u0010&\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u008e\u0001\u0010F\"\u0005\b\u008f\u0001\u0010HR\u001c\u0010%\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0090\u0001\u0010F\"\u0005\b\u0091\u0001\u0010HR\u001c\u00109\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0092\u0001\u0010`\"\u0005\b\u0093\u0001\u0010bR\u001c\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0094\u0001\u0010F\"\u0005\b\u0095\u0001\u0010HR\u001c\u0010\u001d\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0096\u0001\u0010V\"\u0005\b\u0097\u0001\u0010XR\u001c\u0010\u001e\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0098\u0001\u0010`\"\u0005\b\u0099\u0001\u0010bR\u001e\u0010?\u001a\u00020@X\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u009a\u0001\u0010\u009b\u0001\"\u0006\b\u009c\u0001\u0010\u009d\u0001R\u001c\u00106\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u009e\u0001\u0010F\"\u0005\b\u009f\u0001\u0010HR\u001c\u00108\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00a0\u0001\u0010p\"\u0005\b\u00a1\u0001\u0010rR\u001c\u00107\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00a2\u0001\u0010F\"\u0005\b\u00a3\u0001\u0010HR\u001c\u0010.\u001a\u00020/X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00a4\u0001\u0010N\"\u0005\b\u00a5\u0001\u0010PR\u001c\u0010>\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00a6\u0001\u0010`\"\u0005\b\u00a7\u0001\u0010bR\u001c\u0010<\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00a8\u0001\u0010`\"\u0005\b\u00a9\u0001\u0010bR\u001c\u0010=\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00aa\u0001\u0010`\"\u0005\b\u00ab\u0001\u0010bR\u001c\u0010\u0018\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00ac\u0001\u0010V\"\u0005\b\u00ad\u0001\u0010XR%\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010 X\u0086\u000e\u00a2\u0006\u0011\n\u0000\u001a\u0005\b\u00ae\u0001\u0010}\"\u0006\b\u00af\u0001\u0010\u00b0\u0001R\u001c\u0010#\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00b1\u0001\u0010`\"\u0005\b\u00b2\u0001\u0010bR\u001e\u0010!\u001a\u00020\"X\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u00b3\u0001\u0010\u00b4\u0001\"\u0006\b\u00b5\u0001\u0010\u00b6\u0001R\u001e\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u00b7\u0001\u0010\u00b8\u0001\"\u0006\b\u00b9\u0001\u0010\u00ba\u0001R\u001e\u0010\f\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u00bb\u0001\u0010\u00b8\u0001\"\u0006\b\u00bc\u0001\u0010\u00ba\u0001R\u0013\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\n\n\u0000\u001a\u0006\b\u00bd\u0001\u0010\u00be\u0001R\u001c\u0010$\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00bf\u0001\u0010`\"\u0005\b\u00c0\u0001\u0010bR%\u0010\'\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010 X\u0086\u000e\u00a2\u0006\u0011\n\u0000\u001a\u0005\b\u00c1\u0001\u0010}\"\u0006\b\u00c2\u0001\u0010\u00b0\u0001\u00a8\u0006\u00fb\u0001"}, d2 = {"Lcom/cutnow/sdk/model/MediaLayer;", "", "id", "", "type", "Lcom/cutnow/sdk/model/LayerType;", "startTimeMs", "", "durationMs", "content", "transitionIn", "Lcom/cutnow/sdk/model/TransitionType;", "transitionOut", "posX", "", "posY", "scale", "scaleX", "scaleY", "rotation", "isSelected", "", "originalWidth", "originalHeight", "textColor", "", "backgroundColor", "fontFamily", "fontSize", "strokeColor", "strokeWidth", "textGradientColors", "", "textWarpType", "Lcom/cutnow/sdk/model/TextWarpType;", "textWarpIntensity", "volume", "sourceStartTimeMs", "sourceDurationMs", "waveformPoints", "effectType", "Lcom/cutnow/sdk/model/EffectType;", "effectIntensity", "filterType", "Lcom/cutnow/sdk/model/FilterType;", "filterIntensity", "textAnimation", "Lcom/cutnow/sdk/model/TextAnimationType;", "animationIn", "animationOut", "animationLoop", "animInDuration", "animOutDuration", "animLoopDuration", "textAnimInDurationMs", "textAnimOutDurationMs", "textAnimLoopCursor", "speed", "flipHorizontal", "flipVertical", "textBgPaddingX", "textBgPaddingY", "textBgCornerRadius", "textAlignment", "Lcom/cutnow/sdk/model/TextAlignment;", "keyframes", "", "Lcom/cutnow/sdk/model/Keyframe;", "(Ljava/lang/String;Lcom/cutnow/sdk/model/LayerType;JJLjava/lang/String;Lcom/cutnow/sdk/model/TransitionType;Lcom/cutnow/sdk/model/TransitionType;FFFFFFZFFIILjava/lang/String;FIFLjava/util/List;Lcom/cutnow/sdk/model/TextWarpType;FFJJLjava/util/List;Lcom/cutnow/sdk/model/EffectType;FLcom/cutnow/sdk/model/FilterType;FLcom/cutnow/sdk/model/TextAnimationType;Lcom/cutnow/sdk/model/TextAnimationType;Lcom/cutnow/sdk/model/TextAnimationType;Lcom/cutnow/sdk/model/TextAnimationType;JJJJJZFZZFFFLcom/cutnow/sdk/model/TextAlignment;Ljava/util/List;)V", "getAnimInDuration", "()J", "setAnimInDuration", "(J)V", "getAnimLoopDuration", "setAnimLoopDuration", "getAnimOutDuration", "setAnimOutDuration", "getAnimationIn", "()Lcom/cutnow/sdk/model/TextAnimationType;", "setAnimationIn", "(Lcom/cutnow/sdk/model/TextAnimationType;)V", "getAnimationLoop", "setAnimationLoop", "getAnimationOut", "setAnimationOut", "getBackgroundColor", "()I", "setBackgroundColor", "(I)V", "getContent", "()Ljava/lang/String;", "contentHash", "getContentHash", "getDurationMs", "setDurationMs", "getEffectIntensity", "()F", "setEffectIntensity", "(F)V", "getEffectType", "()Lcom/cutnow/sdk/model/EffectType;", "setEffectType", "(Lcom/cutnow/sdk/model/EffectType;)V", "endTimeMs", "getEndTimeMs", "getFilterIntensity", "setFilterIntensity", "getFilterType", "()Lcom/cutnow/sdk/model/FilterType;", "setFilterType", "(Lcom/cutnow/sdk/model/FilterType;)V", "getFlipHorizontal", "()Z", "setFlipHorizontal", "(Z)V", "getFlipVertical", "setFlipVertical", "getFontFamily", "setFontFamily", "(Ljava/lang/String;)V", "getFontSize", "setFontSize", "getId", "setSelected", "getKeyframes", "()Ljava/util/List;", "getOriginalHeight", "setOriginalHeight", "getOriginalWidth", "setOriginalWidth", "getPosX", "setPosX", "getPosY", "setPosY", "getRotation", "setRotation", "getScale", "setScale", "getScaleX", "setScaleX", "getScaleY", "setScaleY", "getSourceDurationMs", "setSourceDurationMs", "getSourceStartTimeMs", "setSourceStartTimeMs", "getSpeed", "setSpeed", "getStartTimeMs", "setStartTimeMs", "getStrokeColor", "setStrokeColor", "getStrokeWidth", "setStrokeWidth", "getTextAlignment", "()Lcom/cutnow/sdk/model/TextAlignment;", "setTextAlignment", "(Lcom/cutnow/sdk/model/TextAlignment;)V", "getTextAnimInDurationMs", "setTextAnimInDurationMs", "getTextAnimLoopCursor", "setTextAnimLoopCursor", "getTextAnimOutDurationMs", "setTextAnimOutDurationMs", "getTextAnimation", "setTextAnimation", "getTextBgCornerRadius", "setTextBgCornerRadius", "getTextBgPaddingX", "setTextBgPaddingX", "getTextBgPaddingY", "setTextBgPaddingY", "getTextColor", "setTextColor", "getTextGradientColors", "setTextGradientColors", "(Ljava/util/List;)V", "getTextWarpIntensity", "setTextWarpIntensity", "getTextWarpType", "()Lcom/cutnow/sdk/model/TextWarpType;", "setTextWarpType", "(Lcom/cutnow/sdk/model/TextWarpType;)V", "getTransitionIn", "()Lcom/cutnow/sdk/model/TransitionType;", "setTransitionIn", "(Lcom/cutnow/sdk/model/TransitionType;)V", "getTransitionOut", "setTransitionOut", "getType", "()Lcom/cutnow/sdk/model/LayerType;", "getVolume", "setVolume", "getWaveformPoints", "setWaveformPoints", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component40", "component41", "component42", "component43", "component44", "component45", "component46", "component47", "component48", "component49", "component5", "component50", "component51", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "cutNowSDK_release"})
public final class MediaLayer {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cutnow.sdk.model.LayerType type = null;
    private long startTimeMs;
    private long durationMs;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String content = null;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.TransitionType transitionIn;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.TransitionType transitionOut;
    private float posX;
    private float posY;
    private float scale;
    private float scaleX;
    private float scaleY;
    private float rotation;
    private boolean isSelected;
    private float originalWidth;
    private float originalHeight;
    private int textColor;
    private int backgroundColor;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String fontFamily;
    private float fontSize;
    private int strokeColor;
    private float strokeWidth;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<java.lang.Integer> textGradientColors;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.TextWarpType textWarpType;
    private float textWarpIntensity;
    private float volume;
    private long sourceStartTimeMs;
    private long sourceDurationMs;
    @org.jetbrains.annotations.Nullable()
    private java.util.List<java.lang.Float> waveformPoints;
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.model.EffectType effectType;
    private float effectIntensity;
    @org.jetbrains.annotations.Nullable()
    private com.cutnow.sdk.model.FilterType filterType;
    private float filterIntensity;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.TextAnimationType textAnimation;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.TextAnimationType animationIn;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.TextAnimationType animationOut;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.TextAnimationType animationLoop;
    private long animInDuration;
    private long animOutDuration;
    private long animLoopDuration;
    private long textAnimInDurationMs;
    private long textAnimOutDurationMs;
    private boolean textAnimLoopCursor;
    private float speed;
    private boolean flipHorizontal;
    private boolean flipVertical;
    private float textBgPaddingX;
    private float textBgPaddingY;
    private float textBgCornerRadius;
    @org.jetbrains.annotations.NotNull()
    private com.cutnow.sdk.model.TextAlignment textAlignment;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cutnow.sdk.model.Keyframe> keyframes = null;
    
    public MediaLayer(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.LayerType type, long startTimeMs, long durationMs, @org.jetbrains.annotations.NotNull()
    java.lang.String content, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType transitionIn, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType transitionOut, float posX, float posY, float scale, float scaleX, float scaleY, float rotation, boolean isSelected, float originalWidth, float originalHeight, int textColor, int backgroundColor, @org.jetbrains.annotations.NotNull()
    java.lang.String fontFamily, float fontSize, int strokeColor, float strokeWidth, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Integer> textGradientColors, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextWarpType textWarpType, float textWarpIntensity, float volume, long sourceStartTimeMs, long sourceDurationMs, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Float> waveformPoints, @org.jetbrains.annotations.Nullable()
    com.cutnow.sdk.model.EffectType effectType, float effectIntensity, @org.jetbrains.annotations.Nullable()
    com.cutnow.sdk.model.FilterType filterType, float filterIntensity, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType textAnimation, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType animationIn, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType animationOut, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType animationLoop, long animInDuration, long animOutDuration, long animLoopDuration, long textAnimInDurationMs, long textAnimOutDurationMs, boolean textAnimLoopCursor, float speed, boolean flipHorizontal, boolean flipVertical, float textBgPaddingX, float textBgPaddingY, float textBgCornerRadius, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAlignment textAlignment, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Keyframe> keyframes) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.LayerType getType() {
        return null;
    }
    
    public final long getStartTimeMs() {
        return 0L;
    }
    
    public final void setStartTimeMs(long p0) {
    }
    
    public final long getDurationMs() {
        return 0L;
    }
    
    public final void setDurationMs(long p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getContent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TransitionType getTransitionIn() {
        return null;
    }
    
    public final void setTransitionIn(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TransitionType getTransitionOut() {
        return null;
    }
    
    public final void setTransitionOut(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType p0) {
    }
    
    public final float getPosX() {
        return 0.0F;
    }
    
    public final void setPosX(float p0) {
    }
    
    public final float getPosY() {
        return 0.0F;
    }
    
    public final void setPosY(float p0) {
    }
    
    public final float getScale() {
        return 0.0F;
    }
    
    public final void setScale(float p0) {
    }
    
    public final float getScaleX() {
        return 0.0F;
    }
    
    public final void setScaleX(float p0) {
    }
    
    public final float getScaleY() {
        return 0.0F;
    }
    
    public final void setScaleY(float p0) {
    }
    
    public final float getRotation() {
        return 0.0F;
    }
    
    public final void setRotation(float p0) {
    }
    
    public final boolean isSelected() {
        return false;
    }
    
    public final void setSelected(boolean p0) {
    }
    
    public final float getOriginalWidth() {
        return 0.0F;
    }
    
    public final void setOriginalWidth(float p0) {
    }
    
    public final float getOriginalHeight() {
        return 0.0F;
    }
    
    public final void setOriginalHeight(float p0) {
    }
    
    public final int getTextColor() {
        return 0;
    }
    
    public final void setTextColor(int p0) {
    }
    
    public final int getBackgroundColor() {
        return 0;
    }
    
    public final void setBackgroundColor(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFontFamily() {
        return null;
    }
    
    public final void setFontFamily(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final float getFontSize() {
        return 0.0F;
    }
    
    public final void setFontSize(float p0) {
    }
    
    public final int getStrokeColor() {
        return 0;
    }
    
    public final void setStrokeColor(int p0) {
    }
    
    public final float getStrokeWidth() {
        return 0.0F;
    }
    
    public final void setStrokeWidth(float p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Integer> getTextGradientColors() {
        return null;
    }
    
    public final void setTextGradientColors(@org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Integer> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextWarpType getTextWarpType() {
        return null;
    }
    
    public final void setTextWarpType(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextWarpType p0) {
    }
    
    public final float getTextWarpIntensity() {
        return 0.0F;
    }
    
    public final void setTextWarpIntensity(float p0) {
    }
    
    public final float getVolume() {
        return 0.0F;
    }
    
    public final void setVolume(float p0) {
    }
    
    public final long getSourceStartTimeMs() {
        return 0L;
    }
    
    public final void setSourceStartTimeMs(long p0) {
    }
    
    public final long getSourceDurationMs() {
        return 0L;
    }
    
    public final void setSourceDurationMs(long p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Float> getWaveformPoints() {
        return null;
    }
    
    public final void setWaveformPoints(@org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Float> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cutnow.sdk.model.EffectType getEffectType() {
        return null;
    }
    
    public final void setEffectType(@org.jetbrains.annotations.Nullable()
    com.cutnow.sdk.model.EffectType p0) {
    }
    
    public final float getEffectIntensity() {
        return 0.0F;
    }
    
    public final void setEffectIntensity(float p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cutnow.sdk.model.FilterType getFilterType() {
        return null;
    }
    
    public final void setFilterType(@org.jetbrains.annotations.Nullable()
    com.cutnow.sdk.model.FilterType p0) {
    }
    
    public final float getFilterIntensity() {
        return 0.0F;
    }
    
    public final void setFilterIntensity(float p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextAnimationType getTextAnimation() {
        return null;
    }
    
    public final void setTextAnimation(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextAnimationType getAnimationIn() {
        return null;
    }
    
    public final void setAnimationIn(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextAnimationType getAnimationOut() {
        return null;
    }
    
    public final void setAnimationOut(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextAnimationType getAnimationLoop() {
        return null;
    }
    
    public final void setAnimationLoop(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType p0) {
    }
    
    public final long getAnimInDuration() {
        return 0L;
    }
    
    public final void setAnimInDuration(long p0) {
    }
    
    public final long getAnimOutDuration() {
        return 0L;
    }
    
    public final void setAnimOutDuration(long p0) {
    }
    
    public final long getAnimLoopDuration() {
        return 0L;
    }
    
    public final void setAnimLoopDuration(long p0) {
    }
    
    public final long getTextAnimInDurationMs() {
        return 0L;
    }
    
    public final void setTextAnimInDurationMs(long p0) {
    }
    
    public final long getTextAnimOutDurationMs() {
        return 0L;
    }
    
    public final void setTextAnimOutDurationMs(long p0) {
    }
    
    public final boolean getTextAnimLoopCursor() {
        return false;
    }
    
    public final void setTextAnimLoopCursor(boolean p0) {
    }
    
    public final float getSpeed() {
        return 0.0F;
    }
    
    public final void setSpeed(float p0) {
    }
    
    public final boolean getFlipHorizontal() {
        return false;
    }
    
    public final void setFlipHorizontal(boolean p0) {
    }
    
    public final boolean getFlipVertical() {
        return false;
    }
    
    public final void setFlipVertical(boolean p0) {
    }
    
    public final float getTextBgPaddingX() {
        return 0.0F;
    }
    
    public final void setTextBgPaddingX(float p0) {
    }
    
    public final float getTextBgPaddingY() {
        return 0.0F;
    }
    
    public final void setTextBgPaddingY(float p0) {
    }
    
    public final float getTextBgCornerRadius() {
        return 0.0F;
    }
    
    public final void setTextBgCornerRadius(float p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextAlignment getTextAlignment() {
        return null;
    }
    
    public final void setTextAlignment(@org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAlignment p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Keyframe> getKeyframes() {
        return null;
    }
    
    public final long getEndTimeMs() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getContentHash() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final float component10() {
        return 0.0F;
    }
    
    public final float component11() {
        return 0.0F;
    }
    
    public final float component12() {
        return 0.0F;
    }
    
    public final float component13() {
        return 0.0F;
    }
    
    public final boolean component14() {
        return false;
    }
    
    public final float component15() {
        return 0.0F;
    }
    
    public final float component16() {
        return 0.0F;
    }
    
    public final int component17() {
        return 0;
    }
    
    public final int component18() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.LayerType component2() {
        return null;
    }
    
    public final float component20() {
        return 0.0F;
    }
    
    public final int component21() {
        return 0;
    }
    
    public final float component22() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Integer> component23() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextWarpType component24() {
        return null;
    }
    
    public final float component25() {
        return 0.0F;
    }
    
    public final float component26() {
        return 0.0F;
    }
    
    public final long component27() {
        return 0L;
    }
    
    public final long component28() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.Float> component29() {
        return null;
    }
    
    public final long component3() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cutnow.sdk.model.EffectType component30() {
        return null;
    }
    
    public final float component31() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cutnow.sdk.model.FilterType component32() {
        return null;
    }
    
    public final float component33() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextAnimationType component34() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextAnimationType component35() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextAnimationType component36() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextAnimationType component37() {
        return null;
    }
    
    public final long component38() {
        return 0L;
    }
    
    public final long component39() {
        return 0L;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final long component40() {
        return 0L;
    }
    
    public final long component41() {
        return 0L;
    }
    
    public final long component42() {
        return 0L;
    }
    
    public final boolean component43() {
        return false;
    }
    
    public final float component44() {
        return 0.0F;
    }
    
    public final boolean component45() {
        return false;
    }
    
    public final boolean component46() {
        return false;
    }
    
    public final float component47() {
        return 0.0F;
    }
    
    public final float component48() {
        return 0.0F;
    }
    
    public final float component49() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TextAlignment component50() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cutnow.sdk.model.Keyframe> component51() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TransitionType component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.TransitionType component7() {
        return null;
    }
    
    public final float component8() {
        return 0.0F;
    }
    
    public final float component9() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cutnow.sdk.model.MediaLayer copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.LayerType type, long startTimeMs, long durationMs, @org.jetbrains.annotations.NotNull()
    java.lang.String content, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType transitionIn, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TransitionType transitionOut, float posX, float posY, float scale, float scaleX, float scaleY, float rotation, boolean isSelected, float originalWidth, float originalHeight, int textColor, int backgroundColor, @org.jetbrains.annotations.NotNull()
    java.lang.String fontFamily, float fontSize, int strokeColor, float strokeWidth, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Integer> textGradientColors, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextWarpType textWarpType, float textWarpIntensity, float volume, long sourceStartTimeMs, long sourceDurationMs, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.Float> waveformPoints, @org.jetbrains.annotations.Nullable()
    com.cutnow.sdk.model.EffectType effectType, float effectIntensity, @org.jetbrains.annotations.Nullable()
    com.cutnow.sdk.model.FilterType filterType, float filterIntensity, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType textAnimation, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType animationIn, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType animationOut, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAnimationType animationLoop, long animInDuration, long animOutDuration, long animLoopDuration, long textAnimInDurationMs, long textAnimOutDurationMs, boolean textAnimLoopCursor, float speed, boolean flipHorizontal, boolean flipVertical, float textBgPaddingX, float textBgPaddingY, float textBgCornerRadius, @org.jetbrains.annotations.NotNull()
    com.cutnow.sdk.model.TextAlignment textAlignment, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cutnow.sdk.model.Keyframe> keyframes) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}