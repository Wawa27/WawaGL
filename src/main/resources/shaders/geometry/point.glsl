#version 460

layout(points) in;
layout(line_strip, max_vertices = 6) out;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 model;

void main() {
    vec3 position = gl_in[0].gl_Position.xyz;

    gl_Position = projection * view * model * (vec4(position, 1.0));
    EmitVertex();
    gl_Position = projection * view * model * (vec4(position + vec3(0, 128, 0), 1.0));
    EmitVertex();
    EndPrimitive();
}
