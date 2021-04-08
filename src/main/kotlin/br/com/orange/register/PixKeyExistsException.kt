package br.com.orange.register

import java.lang.RuntimeException

class PixKeyExistsException(s: String) : RuntimeException(s) {
}
