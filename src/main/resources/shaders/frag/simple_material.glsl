#version 450

struct Material {
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    vec3 emissive;
    float density;
    float specular_exponent;
    float transparency;
    int illum;
};

in vec3 aPosition;
in vec3 aNormal;

out vec4 gl_FragColor;

uniform Material material = {
    vec3(1, 1, 1),
    vec3(1, 1, 1),
    vec3(1, 1, 1),
    vec3(1, 1, 1),
    1.0,
    1.0,
    1.0,
    1
};

void main() {
    gl_FragColor = vec4(material.ambient, 1.0);
}
